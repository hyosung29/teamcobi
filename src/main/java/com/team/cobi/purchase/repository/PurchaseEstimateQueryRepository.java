package com.team.cobi.purchase.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.purchase.dto.PurchaseEstimateDetailResponse;
import com.team.cobi.purchase.dto.PurchaseEstimateListResponse;
import com.team.cobi.purchase.dto.SearchPurchaseEstimateList;
import com.team.cobi.purchase.entity.PurchaseEstimate;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;
import static com.team.cobi.product.entity.QProduct.product;
import static com.team.cobi.purchase.entity.QPurchaseEstimate.purchaseEstimate;
import static com.team.cobi.sales.entity.QClient.client;


@Repository
public class PurchaseEstimateQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;


    public PurchaseEstimateQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(PurchaseEstimate.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<PurchaseEstimateListResponse> searchPage(SearchPurchaseEstimateList search, Pageable pageable) {
        JPAQuery<PurchaseEstimateListResponse> list = searchAll(search);
        // 아래에 입력한 searchAll 쿼리문을 applyPagination 함수가 페이징처리 및 리밋 오프셋 솔트(정렬) 기능을 해줌. .fetch는 조회라는 뜻
        List<PurchaseEstimateListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = searchTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Long searchTotalCount(SearchPurchaseEstimateList search) {
        // 게시글 Total 수 가져오기
        return jpaQueryFactory
                .select(purchaseEstimate.count())
                .from(purchaseEstimate)
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(purchaseEstimate.createdBy))
                .leftJoin(client)
                .on(client.id.eq(purchaseEstimate.clientId))
                .leftJoin(product)
                .on(product.id.eq(purchaseEstimate.productId))
                .where(
                        //  deleteFlag 가 false 인 것만 조회
                        purchaseEstimate.deleteFlag.isFalse(),
                        likeClientName(search.getClientName())
                )
                // 1건만 조회 = count 니까..
                .fetchOne();
    }

    public JPAQuery<PurchaseEstimateListResponse> searchAll(SearchPurchaseEstimateList search) {
        return jpaQueryFactory
                //
                .select(Projections.bean(PurchaseEstimateListResponse.class,
                        purchaseEstimate.id,
                        product.id.as("productId"),
                        product.productName,
                        purchaseEstimate.productQuantity,
                        product.unitPrice,
                        purchaseEstimate.supplyValue,
                        purchaseEstimate.tax,
                        purchaseEstimate.totalPrice,
                        purchaseEstimate.status,
                        employee.name,
                        purchaseEstimate.createdDate,
                        client.id.as("clientId"),
                        client.clientName,
                        client.type
                ))
                .from(purchaseEstimate)
                // 작성자가 null 인값도 포함하여 조회
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(purchaseEstimate.createdBy))
                .leftJoin(client)
                .on(client.id.eq(purchaseEstimate.clientId))
                .leftJoin(product)
                .on(product.id.eq(purchaseEstimate.productId))
                .where(
                        purchaseEstimate.deleteFlag.isFalse(),
                        likeClientName(search.getClientName()),
                        client.type.eq(purchaseEstimate.type)
                );
    }

    private BooleanExpression likeClientName(String clientName) {
        return StringUtils.isNotEmpty(clientName) ? client.clientName.like("%" + clientName + "%") : null;
    }

    public PurchaseEstimateDetailResponse getPurchaseEstimateDetail(String id) {
        return jpaQueryFactory
                .select(Projections.bean(PurchaseEstimateDetailResponse.class,
                        purchaseEstimate.id,
                        purchaseEstimate.clientId,
                        client.clientName,
                        purchaseEstimate.productId,
                        product.productName,
                        purchaseEstimate.productQuantity,
                        purchaseEstimate.totalPrice,
                        purchaseEstimate.createdDate,
                        purchaseEstimate.status,
                        purchaseEstimate.type,
                        product.unitPrice
                ))
                .from(purchaseEstimate)
                .leftJoin(client)
                .on(purchaseEstimate.clientId.eq(client.id))
                .leftJoin(product)
                .on(purchaseEstimate.productId.eq(product.id))
                .where(
                        purchaseEstimate.id.eq(id),
                        purchaseEstimate.deleteFlag.isFalse(),
                        client.deleteFlag.isFalse(),
                        product.deleteFlag.isFalse()
                )
                .fetchOne();
    }
}