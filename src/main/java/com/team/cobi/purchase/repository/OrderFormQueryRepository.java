package com.team.cobi.purchase.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.purchase.dto.OrderFormDetailResponse;
import com.team.cobi.purchase.dto.OrderFormListResponse;
import com.team.cobi.purchase.dto.SearchPurchaseOrderFormList;
import com.team.cobi.purchase.entity.OrderForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.team.cobi.logistics.entity.QInventory.inventory;
import static com.team.cobi.logistics.entity.QSection.section;
import static com.team.cobi.logistics.entity.QWarehouse.warehouse;
import static com.team.cobi.product.entity.QProduct.product;
import static com.team.cobi.purchase.entity.QOrderForm.orderForm;
import static com.team.cobi.purchase.entity.QPurchaseEstimate.purchaseEstimate;
import static com.team.cobi.sales.entity.QClient.client;
import static com.team.cobi.sales.entity.QSalesEstimate.salesEstimate;


@Repository
public class OrderFormQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    // QuerydslRepositorySupport 쓰기 위해서 @RequiredArgsConstructor 사용 안하고 직접 생성
    public OrderFormQueryRepository(JPAQueryFactory jpaQueryFactory) {
        // 필수 super(Entity.class) - 해당 Repositoey의 Entity로 작성
        super(OrderForm.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<OrderFormListResponse> searchPurchasePage(SearchPurchaseOrderFormList search, Pageable pageable) {
        // Query
        JPAQuery<OrderFormListResponse> list = searchPurchaseAll(search);
        // 작성된 Query 에서 자동으로 Paging 처리(Repository 에 QuerydslRepositorySupport 클래스를 extends 해줘야 사용가능)
        // 페이징 처리된 Query 문을 fetch() - 이 때 select 쿼리 발생
        // 아래에 입력한 searchAll 쿼리문을 applyPagination 함수가 페이징처리 및 리밋 오프셋 솔트(정렬) 기능을 해줌. .fetch는 조회라는 뜻
        List<OrderFormListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();

        long totalCount = searchTotalCount(search);

        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Page<OrderFormListResponse> searchSalesPage(SearchPurchaseOrderFormList search, Pageable pageable) {
        // Query
        JPAQuery<OrderFormListResponse> list = searchSalesAll(search);
        // 작성된 Query 에서 자동으로 Paging 처리(Repository 에 QuerydslRepositorySupport 클래스를 extends 해줘야 사용가능)
        // 페이징 처리된 Query 문을 fetch() - 이 때 select 쿼리 발생
        // 아래에 입력한 searchAll 쿼리문을 applyPagination 함수가 페이징처리 및 리밋 오프셋 솔트(정렬) 기능을 해줌. .fetch는 조회라는 뜻
        List<OrderFormListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();

        long totalCount = searchTotalCount(search);

        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Long searchTotalCount(SearchPurchaseOrderFormList search) {
        // 게시글 Total 수 가져오기
        return jpaQueryFactory
                // entity명과 동일해야 함
                .select(orderForm.count())
                .from(orderForm)
                .leftJoin(salesEstimate)
                .on(orderForm.estimateId.eq(salesEstimate.id))
                .leftJoin(client)
                .on(salesEstimate.clientId.eq(client.id))
                .leftJoin(product)
                .on(salesEstimate.productId.eq(product.id))
                .where(
                        //  deleteFlag 가 false 인 것만 조회
                        orderForm.deleteFlag.isFalse(),
                        likeClientName(search.getClientName())
                )
                // 1건만 조회 = count 니까..
                .fetchOne();
    }

    public JPAQuery<OrderFormListResponse> searchPurchaseAll(SearchPurchaseOrderFormList search) {
        return jpaQueryFactory
                .select(Projections.bean(OrderFormListResponse.class,
                        orderForm.id,
                        orderForm.estimateId,
                        orderForm.type,
                        client.clientName,
                        purchaseEstimate.productId,
                        product.productName,
                        purchaseEstimate.productQuantity,
                        purchaseEstimate.totalPrice,
                        orderForm.createdDate,
                        orderForm.modifiedDate,
                        orderForm.status
                ))
                .from(orderForm)
                .leftJoin(purchaseEstimate)
                .on(orderForm.estimateId.eq(purchaseEstimate.id))
                .leftJoin(client)
                .on(purchaseEstimate.clientId.eq(client.id))
                .leftJoin(product)
                .on(purchaseEstimate.productId.eq(product.id))
                .where(
                        orderForm.deleteFlag.isFalse(),
                        orderForm.type.eq("구매"),
                        likeClientName(search.getClientName()),
                        likeProductName(search.getProductName()),
                        likeStatus(search.getStatus()),
                        likeCreatedDate(search.getCreatedDate())
                );
    }

    public JPAQuery<OrderFormListResponse> searchSalesAll(SearchPurchaseOrderFormList search) {
        return jpaQueryFactory
                .select(Projections.bean(OrderFormListResponse.class,
                        orderForm.id,
                        orderForm.estimateId,
                        orderForm.type,
                        client.clientName,
                        salesEstimate.productId,
                        product.productName,
                        salesEstimate.productQuantity,
                        salesEstimate.totalPrice,
                        orderForm.modifiedDate,
                        orderForm.createdDate,
                        orderForm.status,
                        warehouse.warehouseName,
                        section.sectionName
                ))
                .from(orderForm)
                .leftJoin(salesEstimate)
                .on(orderForm.estimateId.eq(salesEstimate.id))
                .leftJoin(product)
                .on(salesEstimate.productId.eq(product.id))
                .leftJoin(client)
                .on(salesEstimate.clientId.eq(client.id))
                .leftJoin(inventory)
                .on(salesEstimate.productId.eq(inventory.productId))
                .leftJoin(warehouse)
                .on(salesEstimate.warehouseId.eq(warehouse.id))
                .leftJoin(section)
                .on(salesEstimate.sectionId.eq(section.sectionId))
                .where(
                        orderForm.deleteFlag.isFalse(),
                        salesEstimate.deleteFlag.isFalse(),
                        product.deleteFlag.isFalse(),
                        client.deleteFlag.isFalse(),
                        orderForm.type.eq("판매"),
                        likeClientName(search.getClientName()),
                        likeProductName(search.getProductName()),
                        likeStatus(search.getStatus()),
                        likeCreatedDate(search.getCreatedDate())
                );
    }

    public OrderFormDetailResponse getPurchaseFormDetail(String id) {
        return jpaQueryFactory
                .select(Projections.bean(OrderFormDetailResponse.class,
                        orderForm.id,
                        orderForm.purchaseNumber,
                        orderForm.estimateId,
                        orderForm.type,
                        client.clientName,
                        purchaseEstimate.productId,
                        product.productName,
                        purchaseEstimate.productQuantity,
                        purchaseEstimate.totalPrice,
                        orderForm.createdDate,
                        orderForm.status
                ))
                .from(orderForm)
                .leftJoin(purchaseEstimate)
                .on(orderForm.estimateId.eq(purchaseEstimate.id))
                .leftJoin(client)
                .on(purchaseEstimate.clientId.eq(client.id))
                .leftJoin(product)
                .on(purchaseEstimate.productId.eq(product.id))
                .where(
                        orderForm.id.eq(id),
                        orderForm.deleteFlag.isFalse()
                )
                .fetchOne();
    }

    public OrderFormDetailResponse getSalesFormDetail(String id) {
        return jpaQueryFactory
                .select(Projections.bean(OrderFormDetailResponse.class,
                        orderForm.id,
                        orderForm.salesNumber,
                        orderForm.estimateId,
                        orderForm.type,
                        client.clientName,
                        product.productName,
                        orderForm.createdDate,
                        orderForm.status,
                        salesEstimate.productId,
                        salesEstimate.productQuantity,
                        salesEstimate.totalPrice,
                        warehouse.warehouseName,
                        section.sectionName
                ))
                .from(orderForm)
                .leftJoin(salesEstimate)
                .on(orderForm.estimateId.eq(salesEstimate.id))
                .leftJoin(product)
                .on(salesEstimate.productId.eq(product.id))
                .leftJoin(client)
                .on(salesEstimate.clientId.eq(client.id))
                .leftJoin(inventory)
                .on(salesEstimate.productId.eq(inventory.productId))
                .leftJoin(warehouse)
                .on(salesEstimate.warehouseId.eq(warehouse.id))
                .leftJoin(section)
                .on(salesEstimate.sectionId.eq(section.sectionId))
                .where(
                        orderForm.id.eq(id),
                        orderForm.deleteFlag.isFalse()
                )
                .fetchOne();
    }

    private BooleanExpression likeClientName(String clientName) {
        if (StringUtils.isNotEmpty(clientName)) {
            return client.clientName.like("%" + clientName + "%");
        }
        return null;
    }

    private BooleanExpression likeProductName(String productName) {
        if (StringUtils.isNotEmpty(productName)) {
            return product.productName.like("%" + productName + "%");
        }
        return null;
    }

    private BooleanExpression likeStatus(String status) {
        if (StringUtils.isNotEmpty(status)) {
            return orderForm.status.like("%" + status + "%");
        }
        return null;
    }

    private BooleanExpression likeCreatedDate(LocalDateTime createdDate) {
        if (createdDate != null) {
            return orderForm.createdDate.eq(createdDate);
        }
        return null;
    }

}


