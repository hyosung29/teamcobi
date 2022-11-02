package com.team.cobi.logistics.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.logistics.dto.InboundListResponse;
import com.team.cobi.logistics.dto.SearchInboundList;
import com.team.cobi.logistics.entity.Inbound;
import com.team.cobi.purchase.entity.QPurchaseEstimate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.logistics.entity.QInbound.inbound;
import static com.team.cobi.logistics.entity.QSection.section;
import static com.team.cobi.logistics.entity.QWarehouse.warehouse;
import static com.team.cobi.product.entity.QProduct.product;
import static com.team.cobi.purchase.entity.QOrderForm.orderForm;
import static com.team.cobi.purchase.entity.QPurchaseEstimate.purchaseEstimate;
import static com.team.cobi.sales.entity.QClient.client;


@Repository
@Slf4j
public class InboundQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;


    public InboundQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Inbound.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<InboundListResponse> inboundPage(SearchInboundList search, Pageable pageable) {
        log.info(search.getClientName());
        JPAQuery<InboundListResponse> list = searchAll(search);
        List<InboundListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = inboundTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Page<InboundListResponse> inboundHoldPage(SearchInboundList search, Pageable pageable) {
        JPAQuery<InboundListResponse> list = inboundHoldList(search);
        List<InboundListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = inboundTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Long inboundTotalCount(SearchInboundList search) {
        return jpaQueryFactory
                .select(inbound.count())
                .from(inbound)
                .leftJoin(warehouse)
                .on(inbound.warehouseId.eq(warehouse.id))
                .leftJoin(orderForm)
                .on(inbound.orderFormId.eq(orderForm.id))
                .leftJoin(purchaseEstimate)
                .on(orderForm.estimateId.eq(purchaseEstimate.id))
                .leftJoin(client)
                .on(purchaseEstimate.clientId.eq(client.id))
                .leftJoin(product)
                .on(purchaseEstimate.productId.eq(product.id))
                .where(
                        inbound.deleteFlag.isFalse(),
                        likeClient(search.getClientName())
                )
                .fetchOne();
    }

    public JPAQuery<InboundListResponse> searchAll(SearchInboundList search) {
        return jpaQueryFactory
                .select(Projections.bean(InboundListResponse.class,
                        inbound.id,
                        inbound.inboundDate,
                        inbound.orderFormId,
                        inbound.warehouseId,
                        inbound.sectionId,
                        section.sectionName,
                        inbound.status,
                        inbound.createdBy,
                        inbound.modifiedBy,
                        inbound.createdDate,
                        inbound.modifiedDate,
                        warehouse.warehouseName,
                        warehouse.volume,
                        purchaseEstimate.productQuantity,
                        client.clientName,
                        product.productName
                ))
                .from(inbound)
                .leftJoin(warehouse)
                .on(inbound.warehouseId.eq(warehouse.id))
                .leftJoin(orderForm)
                .on(inbound.orderFormId.eq(orderForm.id))
                .leftJoin(purchaseEstimate)
                .on(orderForm.estimateId.eq(purchaseEstimate.id))
                .leftJoin(client)
                .on(purchaseEstimate.clientId.eq(client.id))
                .leftJoin(product)
                .on(purchaseEstimate.productId.eq(product.id))
                .leftJoin(section)
                .on(section.sectionId.eq(inbound.sectionId))
                .where(
                        inbound.deleteFlag.isFalse(),
                        inbound.status.eq("입고완료"),
                        likeClient(search.getClientName()),
                        client.type.like("구매")
                );
    }


    public JPAQuery<InboundListResponse> inboundHoldList(SearchInboundList search) {
        return jpaQueryFactory
                .select(Projections.bean(InboundListResponse.class,
                        inbound.id,
                        inbound.inboundDate,
                        inbound.orderFormId,
                        inbound.warehouseId,
                        inbound.status,
                        inbound.createdBy,
                        inbound.modifiedBy,
                        inbound.createdDate,
                        inbound.modifiedDate,
                        warehouse.warehouseName,
                        warehouse.volume,
                        purchaseEstimate.productQuantity,
                        client.clientName,
                        product.productName
                ))
                .from(inbound)
                .leftJoin(warehouse)
                .on(inbound.warehouseId.eq(warehouse.id))
                .leftJoin(orderForm)
                .on(inbound.orderFormId.eq(orderForm.id))
                .leftJoin(purchaseEstimate)
                .on(orderForm.estimateId.eq(purchaseEstimate.id))
                .leftJoin(client)
                .on(purchaseEstimate.clientId.eq(client.id))
                .leftJoin(product)
                .on(purchaseEstimate.productId.eq(product.id))
                .where(
                        inbound.deleteFlag.isFalse(),
                        inbound.status.eq("입고대기"),
                        likeClient(search.getClientName())
                );
    }


    public InboundListResponse inboundDetail(String id) {
        return jpaQueryFactory
                .select(Projections.bean(InboundListResponse.class,
                        inbound.id,
                        inbound.inboundDate,
                        inbound.orderFormId,
                        inbound.warehouseId,
                        inbound.sectionId,
                        inbound.status,
                        inbound.createdBy,
                        inbound.modifiedBy,
                        inbound.createdDate,
                        inbound.modifiedDate,
                        warehouse.warehouseName,
                        warehouse.volume,
                        section.sectionName,
                        orderForm.status.as("orderFormStatus"),
                        purchaseEstimate.productId,
                        purchaseEstimate.productQuantity,
                        client.clientName,
                        product.productName
                ))
                .from(inbound)
                .leftJoin(warehouse)
                .on(inbound.warehouseId.eq(warehouse.id))
                .leftJoin(section)
                .on(inbound.sectionId.eq(section.sectionId))
                .leftJoin(orderForm)
                .on(inbound.orderFormId.eq(orderForm.id))
                .leftJoin(purchaseEstimate)
                .on(orderForm.estimateId.eq(purchaseEstimate.id))
                .leftJoin(client)
                .on(purchaseEstimate.clientId.eq(client.id))
                .leftJoin(product)
                .on(purchaseEstimate.productId.eq(product.id))
                .where(
                        inbound.deleteFlag.isFalse(),
                        inbound.id.eq(id)
                )
                .fetchOne();
    }

    // 부서명
    private BooleanExpression likeClient(String clientName) {
        return StringUtils.isNotEmpty(clientName) ? client.clientName.like("%" + clientName + "%") : null;
    }

    public JPAQuery<InboundListResponse> getInboundListQ() {
        return jpaQueryFactory
                .select(Projections.bean(InboundListResponse.class,
                        inbound.id,
                        inbound.inboundDate,
                        inbound.status,
                        inbound.orderFormId,
                        inbound.warehouseId,
                        inbound.createdDate,
                        inbound.createdBy,
                        warehouse.warehouseName

                ))
                .from(inbound)
                .leftJoin(warehouse)
                .on(inbound.warehouseId.eq(warehouse.id))
                .leftJoin(orderForm)
                .where(
                        inbound.deleteFlag.isFalse(),
                        warehouse.deleteFlag.isFalse()

                );
    }

}

