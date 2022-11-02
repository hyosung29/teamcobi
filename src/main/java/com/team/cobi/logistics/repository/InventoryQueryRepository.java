package com.team.cobi.logistics.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.logistics.dto.InventoryCodeResponse;
import com.team.cobi.logistics.dto.InventoryListResponse;
import com.team.cobi.logistics.dto.SearchInventoryList;
import com.team.cobi.logistics.entity.Inventory;
import com.team.cobi.logistics.entity.Section;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;
import static com.team.cobi.logistics.entity.QInventory.inventory;
import static com.team.cobi.logistics.entity.QSection.section;
import static com.team.cobi.logistics.entity.QWarehouse.warehouse;
import static com.team.cobi.product.entity.QProduct.product;

@Repository
public class InventoryQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public InventoryQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Section.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<InventoryListResponse> searchPage(SearchInventoryList search, Pageable pageable) {
        JPAQuery<InventoryListResponse> list = searchAll(search);
        List<InventoryListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = searchTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    private Long searchTotalCount(SearchInventoryList search) {
        return jpaQueryFactory
                .select(inventory.count())
                .from(inventory)
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(inventory.createdBy))
                .leftJoin(warehouse)
                .on(warehouse.id.eq(inventory.warehouseId))
                .leftJoin(section)
                .on(section.sectionId.eq(inventory.sectionId))
                .leftJoin(product)
                .on(inventory.productId.eq(product.id))
                .where(
                        inventory.deleteFlag.isFalse(),
                        likeProductName(search.getProductName())
                )
                .fetchOne();
    }

    private JPAQuery<InventoryListResponse> searchAll(SearchInventoryList search) {
        return jpaQueryFactory
                .select(Projections.bean(InventoryListResponse.class,
                        inventory.id,
                        inventory.quantity.sum().as("totalQuantity"),
                        inventory.productId,
                        warehouse.id.as("warehouseId"),
                        warehouse.warehouseName,
                        section.sectionId,
                        employee.name,
                        inventory.createdDate,
                        section.sectionName,
                        product.productName
                ))
                .from(inventory)
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(inventory.createdBy))
                .leftJoin(warehouse)
                .on(warehouse.id.eq(inventory.warehouseId))
                .leftJoin(section)
                .on(section.sectionId.eq(inventory.sectionId))
                .leftJoin(product)
                .on(inventory.productId.eq(product.id))
                .where(
                        section.deleteFlag.isFalse(),
                        likeProductName(search.getProductName())
                )
                .groupBy(inventory.productId);
    }


    private BooleanExpression likeProductName(String productName) {
        return StringUtils.isNotEmpty(productName) ? product.productName.like("%" + productName + "%"): null;
    }



    private JPAQuery<InventoryListResponse> itemTest(SearchInventoryList search) {
        return jpaQueryFactory
                .select(Projections.bean(InventoryListResponse.class,
                        inventory.id,
                        inventory.quantity,
                        inventory.productId,
                        warehouse.id.as("warehouseId"),
                        warehouse.warehouseName,
                        section.sectionId,
                        employee.name,
                        inventory.createdDate,
                        section.sectionName,
                        product.productName
                ))
                .from(inventory)
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(inventory.createdBy))
                .leftJoin(warehouse)
                .on(warehouse.id.eq(inventory.warehouseId))
                .leftJoin(section)
                .on(section.sectionId.eq(inventory.sectionId))
                .leftJoin(product)
                .on(inventory.productId.eq(product.id))
                .where(
                        section.deleteFlag.isFalse(),
                        likeProductName(search.getProductName())
                );
    }

//    public List<InventoryCodeResponse> inventoryNames() {
//        JPAQuery<InventoryListResponse> list = names();
//        List<InventoryListResponse> fetch = getQuerydsl();
//        return fetch;
//    }



    public List<InventoryCodeResponse> getNames() {
        return jpaQueryFactory
                .select(Projections.bean(InventoryCodeResponse.class,
                        inventory.id,
                        inventory.quantity,
                        inventory.productId,
                        inventory.warehouseId,
                        inventory.sectionId,
                        warehouse.warehouseName,
                        product.productName,
                        section.sectionName,
                        product.unitPrice
                ))
                .from(inventory)
                .leftJoin(warehouse)
                .on(inventory.warehouseId.eq(warehouse.id))
                .leftJoin(product)
                .on(inventory.productId.eq(product.id))
                .leftJoin(section)
                .on(inventory.sectionId.eq(section.sectionId))
                .where(
                        inventory.deleteFlag.isFalse(),
                        inventory.quantity.goe(1)
                )
                .fetch();
    }





//    @Query(value="select *\n" +
//            "\t, SUM(quantity) as wareQ\n" +
//            "from teamcobi.inventory\n" +
//            "group by warehouse_id", nativeQuery = true)
//    List<String> getCtl();
//


}