package com.team.cobi.sales.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.purchase.dto.PurchaseEstimateDetailResponse;
import com.team.cobi.sales.dto.SalesEstimateDetailResponse;
import com.team.cobi.sales.dto.SalesEstimateListResponse;
import com.team.cobi.sales.dto.SearchSalesEstimateList;
import com.team.cobi.sales.entity.SalesEstimate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;
import static com.team.cobi.logistics.entity.QInbound.inbound;
import static com.team.cobi.logistics.entity.QInventory.inventory;
import static com.team.cobi.logistics.entity.QSection.section;
import static com.team.cobi.logistics.entity.QWarehouse.warehouse;
import static com.team.cobi.product.entity.QProduct.product;
import static com.team.cobi.purchase.entity.QPurchaseEstimate.purchaseEstimate;
import static com.team.cobi.sales.entity.QClient.client;
import static com.team.cobi.sales.entity.QSalesEstimate.salesEstimate;

@Repository
public class SalesEstimateQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public SalesEstimateQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(SalesEstimate.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<SalesEstimateListResponse> searchPage(SearchSalesEstimateList search, Pageable pageable) {
        JPAQuery<SalesEstimateListResponse> list = searchAll(search);
        List<SalesEstimateListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = searchTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }


    public Long searchTotalCount(SearchSalesEstimateList search) {
        // 게시글 Total 수 가져오기
        return jpaQueryFactory
                .select(salesEstimate.count())
                .from(salesEstimate)
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(salesEstimate.createdBy))
                .leftJoin(client)
                .on(client.id.eq(salesEstimate.clientId))
                .leftJoin(product)
                .on(product.id.eq(salesEstimate.productId))
                .leftJoin(warehouse)
                .on(warehouse.id.eq(salesEstimate.warehouseId))
                .leftJoin(section)
                .on(section.sectionId.eq(salesEstimate.sectionId))
                .leftJoin(inventory)
                .on(inventory.id.eq(salesEstimate.inventoryId))
                .where(
                        //  deleteFlag 가 false 인 것만 조회
                        salesEstimate.deleteFlag.isFalse(),
                        likeClientName(search.getClientName())
                )
                // 1건만 조회 = count 니까..
                .fetchOne();
    }

    public JPAQuery<SalesEstimateListResponse> searchAll(SearchSalesEstimateList search) {
        return jpaQueryFactory
                //
                .select(Projections.bean(SalesEstimateListResponse.class,
                        salesEstimate.id,
                        product.productName,
                        salesEstimate.unitPrice,
                        salesEstimate.productQuantity,
                        salesEstimate.supplyValue,
                        salesEstimate.tax,
                        salesEstimate.totalPrice,
                        salesEstimate.status,
                        employee.name,
                        salesEstimate.createdDate,
                        client.id.as("clientId"),
                        client.clientName,
                        client.type,
                        inventory.id.as("inventoryId"),
                        inventory.warehouseId,
                        warehouse.warehouseName,
                        inventory.sectionId,
                        section.sectionName,
                        inventory.productId
                ))
                .from(salesEstimate)
                // 작성자가 null 인값도 포함하여 조회
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(salesEstimate.createdBy))
                .leftJoin(client)
                .on(client.id.eq(salesEstimate.clientId))
                .leftJoin(product)
                .on(product.id.eq(salesEstimate.productId))
                .leftJoin(warehouse)
                .on(warehouse.id.eq(salesEstimate.warehouseId))
                .leftJoin(section)
                .on(section.sectionId.eq(salesEstimate.sectionId))
                .leftJoin(inventory)
                .on(inventory.id.eq(salesEstimate.inventoryId))
                .where(
                        salesEstimate.deleteFlag.isFalse(),
                        likeClientName(search.getClientName()),
                        client.type.like("판매")
                );
    }


    private BooleanExpression likeClientName(String clientName) {
        return StringUtils.isNotEmpty(clientName) ? client.clientName.like("%" + clientName + "%") : null;
    }

    public SalesEstimateDetailResponse getSalesEstimateDetail(String id) {
        return jpaQueryFactory
                .select(Projections.bean(SalesEstimateDetailResponse.class,
                        salesEstimate.id,
                        salesEstimate.clientId,
                        client.clientName,
                        salesEstimate.productId,
                        product.productName,
                        salesEstimate.productQuantity,
                        salesEstimate.totalPrice,
                        salesEstimate.createdDate,
                        salesEstimate.status,
                        salesEstimate.type,
                        salesEstimate.warehouseId,
                        warehouse.warehouseName,
                        salesEstimate.sectionId,
                        section.sectionName,
                        salesEstimate.inventoryId,
                        product.unitPrice
                ))
                .from(salesEstimate)
                .leftJoin(client)
                .on(salesEstimate.clientId.eq(client.id))
                .leftJoin(product)
                .on(salesEstimate.productId.eq(product.id))
                .leftJoin(warehouse)
                .on(salesEstimate.warehouseId.eq(warehouse.id))
                .leftJoin(section)
                .on(salesEstimate.sectionId.eq(section.sectionId))
                .where(
                        salesEstimate.id.eq(id),
                        salesEstimate.deleteFlag.isFalse(),
                        client.deleteFlag.isFalse(),
                        product.deleteFlag.isFalse()
                )
                .fetchOne();
    }
}

