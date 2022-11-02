package com.team.cobi.purchase.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.sales.dto.SalesClientCodeResponse;
import com.team.cobi.sales.dto.SalesClientListResponse;
import com.team.cobi.sales.dto.SearchSalesClientList;
import com.team.cobi.sales.entity.Client;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;
import static com.team.cobi.product.entity.QProduct.product;
import static com.team.cobi.purchase.entity.QOrderForm.orderForm;
import static com.team.cobi.sales.entity.QClient.client;
import static com.team.cobi.purchase.entity.QPurchaseEstimate.purchaseEstimate;

@Slf4j
@Repository
public class PurchaseClientQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    // QuerydslRepositorySupport 쓰기 위해서 @RequiredArgsConstructor 사용 안하고 직접 생성
    public PurchaseClientQueryRepository(JPAQueryFactory jpaQueryFactory) {
        // ** 필수 super(Entity.class) - 해당 Repository 의 Entity 작성
        super(Client.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<SalesClientListResponse> searchPage(SearchSalesClientList search, Pageable pageable) {
        // Query 작성
        JPAQuery<SalesClientListResponse> list = searchAll(search);
        // 작성된 Query 에서 자동으로 Paging 처리(Repository 에 QuerydslRepositorySupport 클래스를 extends 해줘야 사용가능)
        // 페이징 처리된 Query 문을 fetch() - 이 때 select 쿼리 발생
        List<SalesClientListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        // TotalCount
        long totalCount = searchTotalCount(search);

        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Long searchTotalCount(SearchSalesClientList search) {
        // 게시글 Total 수 가져오기
        return jpaQueryFactory
                .select(client.count())
                .from(client)
                .where(
                        client.deleteFlag.isFalse(),
                        client.type.like("구매"),
                        likeClientName(search.getClientName())
                )
                .fetchOne();
    }
    /*
     *   Projections.bean() - ResponseDto에 변수명 = DB컬럼명 1:1 맵핑하여 dto 에 값을 받아올 수 있음.
     *   별명은 변수뒤에 as("별명") 을 붙여서 사용가능.
     */
    public JPAQuery<SalesClientListResponse> searchAll(SearchSalesClientList search) {
        // 조인해서 employeeNumber 대신 employee-name 으로 가져올 수 있음
        return jpaQueryFactory
                .select(Projections.bean(SalesClientListResponse.class,
                        client.id,
                        client.clientName,
                        client.clientNum,
                        client.registerNum,
                        client.type,
                        client.ceoName,
                        client.telephone,
                        client.address,
                        client.detailAddress,
                        client.email,
                        client.zipCode,
                        client.businessType,
                        client.fax,
                        client.cellPhone,
                        client.registerNum,
                        employee.name,
                        client.createdBy,
                        client.createdDate
                ))
                .from(client)
                // leftJoin 을 해야 createdBy 가 null 값인 데이터도 같이 가져옴
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(client.createdBy))
                .where(
                        client.deleteFlag.isFalse(),
                        client.type.like("구매"),
                        likeClientName(search.getClientName())
                );
        // paging 처리를 해야하기 때문에 이곳에서 fetch() 하지 않음
    }

    public SalesClientCodeResponse clientItem(String id) {
        return jpaQueryFactory
                .select(Projections.bean(SalesClientCodeResponse.class,
                        client.id,
                        client.clientName,
                        client.type,
                        client.ceoName,
                        client.telephone,
                        orderForm.status,
                        product.productName,
                        product.category,
                        product.unitPrice,
                        purchaseEstimate.productQuantity,
                        purchaseEstimate.totalPrice,
                        client.createdBy,
                        client.createdDate
                ))
                .from(client)
                // leftJoin 을 해야 createdBy 가 null 값인 데이터도 같이 가져옴
                .leftJoin(purchaseEstimate)
                .on(purchaseEstimate.clientId.eq(client.id))
                .leftJoin(orderForm)
                .on(purchaseEstimate.id.eq(orderForm.estimateId))
                .leftJoin(product)
                .on(purchaseEstimate.productId.eq(product.id))
                .where(
                        client.deleteFlag.isFalse(),
                        client.id.eq(id)
                )
                .fetchOne();
        //paging 처리를 해야하기 때문에 이곳에서 fetch() 하지 않음
    }

    private BooleanExpression likeClientName(String clientName) {
        log.info("purchase likeClientName 타니 " + clientName);
        if (StringUtils.isNotEmpty(clientName)) {
            return client.clientName.like("%" + clientName + "%");
        }
        return null;
    }
}