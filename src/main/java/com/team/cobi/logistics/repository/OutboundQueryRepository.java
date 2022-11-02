package com.team.cobi.logistics.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.logistics.dto.InboundListResponse;
import com.team.cobi.logistics.dto.OutboundListResponse;
import com.team.cobi.logistics.dto.SearchInboundList;
import com.team.cobi.logistics.entity.Inbound;
import com.team.cobi.logistics.entity.Outbound;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.logistics.entity.QInbound.inbound;
import static com.team.cobi.logistics.entity.QOutbound.outbound;
import static com.team.cobi.logistics.entity.QSection.section;
import static com.team.cobi.logistics.entity.QWarehouse.warehouse;
import static com.team.cobi.product.entity.QProduct.product;
import static com.team.cobi.purchase.entity.QOrderForm.orderForm;
import static com.team.cobi.purchase.entity.QPurchaseEstimate.purchaseEstimate;
import static com.team.cobi.sales.entity.QClient.client;
import static com.team.cobi.sales.entity.QSalesEstimate.salesEstimate;


@Repository
public class OutboundQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;


    public OutboundQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Outbound.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<OutboundListResponse> outboundPage(SearchInboundList search, Pageable pageable) {
        JPAQuery<OutboundListResponse> list = searchAll(search);
        List<OutboundListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = outboundTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Page<OutboundListResponse> outboundHoldPage(SearchInboundList search, Pageable pageable) {
        JPAQuery<OutboundListResponse> list = outboundHoldList(search);
        List<OutboundListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = outboundTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Long outboundTotalCount(SearchInboundList search) {
        // 게시글 Total 수 가져오기
        return jpaQueryFactory
                .select(outbound.count())
                .from(outbound)
                .where(
                        //  deleteFlag 가 false 인 것만 조회
                        outbound.deleteFlag.isFalse(),
                        likeClient(search.getClientName())
                )
                // 1건만 조회 = count 니까..
                .fetchOne();
    }


    public JPAQuery<OutboundListResponse> searchAll(SearchInboundList search) {
        return jpaQueryFactory
                .select(Projections.bean(OutboundListResponse.class,
                        outbound.id,
                        outbound.outboundDate,
                        outbound.orderFormId,
                        warehouse.warehouseName,
                        outbound.status,
                        outbound.createdBy,
                        outbound.modifiedBy,
                        outbound.createdDate,
                        outbound.modifiedDate,
                        orderForm.estimateId,
                        client.clientName,
                        salesEstimate.productId,
                        product.productName,
                        salesEstimate.productQuantity,
                        salesEstimate.warehouseId,
                        warehouse.warehouseName,
                        salesEstimate.sectionId,
                        section.sectionName
                ))
                .from(outbound)
                .leftJoin(orderForm)
                .on(outbound.orderFormId.eq(orderForm.id))
                .leftJoin(salesEstimate)
                .on(orderForm.estimateId.eq(salesEstimate.id))
                .leftJoin(product)
                .on(salesEstimate.productId.eq(product.id))
                .leftJoin(client)
                .on(salesEstimate.clientId.eq(client.id))
                .leftJoin(warehouse)
                .on(salesEstimate.warehouseId.eq(warehouse.id))
                .leftJoin(section)
                .on(salesEstimate.sectionId.eq(section.sectionId))
                .where(
                        outbound.deleteFlag.isFalse(),
                        outbound.status.eq("출고완료")
                        //likeClient(search.getClientName())
                );
    }

    public JPAQuery<OutboundListResponse> outboundHoldList(SearchInboundList search) {
        return jpaQueryFactory
                .select(Projections.bean(OutboundListResponse.class,
                        outbound.id,
                        outbound.status,
                        outbound.createdDate,
                        outbound.createdBy,
                        outbound.deleteFlag,
                        outbound.modifiedBy,
                        outbound.modifiedDate,
                        outbound.orderFormId,
//                        outbound.warehouseId,
                        orderForm.estimateId,
                        client.clientName,
                        salesEstimate.productId,
                        product.productName,
                        salesEstimate.productQuantity,
                        salesEstimate.totalPrice,
                        salesEstimate.warehouseId,
                        warehouse.warehouseName,
                        salesEstimate.sectionId,
                        section.sectionName
                ))
                .from(outbound)
                .leftJoin(orderForm)
                .on(outbound.orderFormId.eq(orderForm.id))
                .leftJoin(salesEstimate)
                .on(orderForm.estimateId.eq(salesEstimate.id))
                .leftJoin(product)
                .on(salesEstimate.productId.eq(product.id))
                .leftJoin(client)
                .on(salesEstimate.clientId.eq(client.id))
                .leftJoin(warehouse)
                .on(salesEstimate.warehouseId.eq(warehouse.id))
                .leftJoin(section)
                .on(salesEstimate.sectionId.eq(section.sectionId))
                .where(
                        outbound.deleteFlag.isFalse(),
                        client.deleteFlag.isFalse(),
                        outbound.status.eq("출고대기"),
                        likeClient(search.getClientName())
                );
    }



    public OutboundListResponse outboundDetail(String id) {
        return jpaQueryFactory
                .select(Projections.bean(OutboundListResponse.class,
                        outbound.id,
                        outbound.outboundDate,
                        outbound.orderFormId,
                        warehouse.warehouseName,
                        outbound.status,
                        outbound.createdBy,
                        outbound.modifiedBy,
                        outbound.createdDate,
                        outbound.modifiedDate,
                        orderForm.estimateId,
                        client.clientName,
                        salesEstimate.productId,
                        product.productName,
                        salesEstimate.productQuantity,
                        salesEstimate.warehouseId,
                        warehouse.warehouseName,
                        salesEstimate.sectionId,
                        section.sectionName
                ))
                .from(outbound)
                .leftJoin(orderForm)
                .on(outbound.orderFormId.eq(orderForm.id))
                .leftJoin(salesEstimate)
                .on(orderForm.estimateId.eq(salesEstimate.id))
                .leftJoin(product)
                .on(salesEstimate.productId.eq(product.id))
                .leftJoin(client)
                .on(salesEstimate.clientId.eq(client.id))
                .leftJoin(warehouse)
                .on(salesEstimate.warehouseId.eq(warehouse.id))
                .leftJoin(section)
                .on(salesEstimate.sectionId.eq(section.sectionId))
                .where(
                        outbound.deleteFlag.isFalse(),
                        outbound.id.eq(id)
                )
                .fetchOne();
    }

    private BooleanExpression likeClient(String clientName) {
        return StringUtils.isNotEmpty(clientName) ? client.clientName.like("%" + clientName + "%") : null;
    }

    public JPAQuery<OutboundListResponse> getOutboundListQ() {
        return jpaQueryFactory
                .select(Projections.bean(OutboundListResponse.class,
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

