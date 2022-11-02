package com.team.cobi.product.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.product.dto.ProductListResponse;
import com.team.cobi.product.dto.SearchProductList;
import com.team.cobi.product.entity.Product;
import com.team.cobi.sales.dto.SalesClientListResponse;
import com.team.cobi.sales.dto.SearchSalesClientList;
import com.team.cobi.sales.entity.Client;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;
import static com.team.cobi.product.entity.QProduct.product;
import static com.team.cobi.sales.entity.QClient.client;

@Repository
public class ProductQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    // QuerydslRepositorySupport 쓰기 위해서 @RequiredArgsConstructor 사용 안하고 직접 생성
    public ProductQueryRepository(JPAQueryFactory jpaQueryFactory) {
        // ** 필수 super(Entity.class) - 해당 Repository 의 Entity 작성
        super(Product.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<ProductListResponse> searchPage(SearchProductList search, Pageable pageable) {
        // Query 작성
        JPAQuery<ProductListResponse> list = searchAll(search);
        // 작성된 Query 에서 자동으로 Paging 처리(Repository 에 QuerydslRepositorySupport 클래스를 extends 해줘야 사용가능)
        // 페이징 처리된 Query 문을 fetch() - 이 때 select 쿼리 발생
        List<ProductListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        // TotalCount
        long totalCount = searchTotalCount(search);

        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Long searchTotalCount(SearchProductList search) {
        // 게시글 Total 수 가져오기
        return jpaQueryFactory
                .select(product.count())
                .from(product)
                .where(
                        product.deleteFlag.isFalse(),
                        likeProductName(search.getProductName())
                )
                .fetchOne();
    }
    /*
     *   Projections.bean() - ResponseDto에 변수명 = DB컬럼명 1:1 맵핑하여 dto 에 값을 받아올 수 있음.
     *   별명은 변수뒤에 as("별명") 을 붙여서 사용가능.
     */
    public JPAQuery<ProductListResponse> searchAll(SearchProductList search) {
        // 조인해서 employeeNumber 대신 employee-name 으로 가져올 수 있음
        return jpaQueryFactory
                .select(Projections.bean(ProductListResponse.class,
                        product.id,
                        product.productName,
                        product.category,
                        product.unitPrice,
                        product.createdDate
                ))
                .from(product)
                // leftJoin 을 해야 createdBy 가 null 값인 데이터도 같이 가져옴
                .where(
                        product.deleteFlag.isFalse(),
                        likeProductName(search.getProductName()),
                        likeCategory(search.getCategory())
                );
        // paging 처리를 해야하기 때문에 이곳에서 fetch() 하지 않음
    }

    private BooleanExpression likeProductName(String productName) {
        if (StringUtils.isNotEmpty(productName)) {
            return product.productName.like("%" + productName + "%");
        }
        return null;
    }

    private BooleanExpression likeCategory(String category) {
        if (StringUtils.isNotEmpty(category)) {
            return product.category.like("%" + category + "%");
        }
        return null;
    }

}
