package com.team.cobi.logistics.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Coalesce;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.logistics.dto.SearchWarehouseList;
import com.team.cobi.logistics.dto.WarehouseCodeResponse;
import com.team.cobi.logistics.dto.WarehouseListResponse;
import com.team.cobi.logistics.entity.Inventory;
import com.team.cobi.logistics.entity.QInventory;
import com.team.cobi.logistics.entity.Warehouse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;
import static com.team.cobi.logistics.entity.QInventory.inventory;
import static com.team.cobi.logistics.entity.QWarehouse.warehouse;
import static com.team.cobi.product.entity.QProduct.product;

@Repository
public class WarehouseQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;


    public WarehouseQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Warehouse.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<WarehouseListResponse> searchPage(SearchWarehouseList search, Pageable pageable) {
        JPAQuery<WarehouseListResponse> list = searchAll(search);
        List<WarehouseListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = searchTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    private Long searchTotalCount(SearchWarehouseList search) {
        return jpaQueryFactory
                .select(warehouse.count())
                .from(warehouse)
                .where(
                        warehouse.deleteFlag.isFalse(),
                        likeWarehouseName(search.getWarehouseName())
                )
                .fetchOne();
    }

    private JPAQuery<WarehouseListResponse> searchAll(SearchWarehouseList search) {
        return jpaQueryFactory
                .select(Projections.bean(WarehouseListResponse.class,
                        warehouse.id,
                        employee.name,
                        warehouse.warehouseName,
                        warehouse.volume,
                        warehouse.status,
                        inventory.quantity
                ))
                .from(warehouse)
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(warehouse.createdBy))
                .leftJoin(inventory)
                .on(warehouse.id.eq(inventory.warehouseId))
                .where(
                        warehouse.deleteFlag.isFalse(),
                        likeWarehouseName(search.getWarehouseName())
                );
    }

//    private JPAQuery<WarehouseListResponse> sumAll(SearchWarehouseList search) {
//        return jpaQueryFactory
//                .select(Projections.bean(WarehouseListResponse.class,
//                        warehouse.id,
//                        employee.name,
//                        warehouse.warehouseName,
//                        inventory.quantity.sum().as("quantity"),
//                        warehouse.volume,
//                        warehouse.status
//                ))
//                .from(warehouse)
//                .leftJoin(employee)
//                .on(employee.employeeNumber.eq(warehouse.createdBy))
//                .leftJoin(inventory)
//                .on(warehouse.id.eq(inventory.warehouseId))
//                .where(
//                        warehouse.deleteFlag.isFalse(),
//                        likeWarehouseName(search.getWarehouseName())
//                ).groupBy(inventory.warehouseId);
//    }

    public WarehouseCodeResponse getWarehouseName(String id) {
        return jpaQueryFactory
                .select(Projections.bean(WarehouseCodeResponse.class,
//                        Coalesce(inventory.quantity.sum(), 0)
                        warehouse.id,
                        warehouse.warehouseName,
                        warehouse.volume,
                        inventory.quantity.sum().as("capacity")
//                        inventory.quantity.sum().coalesce(1).as("capacity")
                        //inventory.quantity.coalesce(inventory.quantity.sum().as("capacity"))
                        //inventory.quantity.sum().coalesce(0)
//                        coalesce(inventory.quantity.sum().as("capacity"), 0)
                ))
                .from(inventory)
                .leftJoin(warehouse)
                .on(inventory.warehouseId.eq(warehouse.id))
                .leftJoin(product)
                .on(inventory.productId.eq(product.id))
                .where(
                        warehouse.deleteFlag.isFalse(),
                        warehouse.status.like("사용중"),
                        warehouse.id.eq(id)
                )
                .fetchOne();
    }

    private BooleanExpression likeWarehouseName(String warehouseName){
        return StringUtils.isNotEmpty(warehouseName) ? warehouse.warehouseName.like("%" + warehouseName + "%") : null;
    }

}