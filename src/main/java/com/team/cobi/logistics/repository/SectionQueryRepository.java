package com.team.cobi.logistics.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.logistics.dto.SearchSectionList;
import com.team.cobi.logistics.dto.SectionListResponse;
import com.team.cobi.logistics.entity.Section;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.logistics.entity.QInventory.inventory;
import static com.team.cobi.logistics.entity.QSection.section;
import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;
import static com.team.cobi.logistics.entity.QWarehouse.warehouse;

@Repository
public class SectionQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;


    public SectionQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Section.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<SectionListResponse> searchPage(SearchSectionList search, Pageable pageable) {
        JPAQuery<SectionListResponse> list = searchAll(search);
        List<SectionListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = searchTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    private Long searchTotalCount(SearchSectionList search) {
        return jpaQueryFactory
                .select(section.count())
                .from(section)
                .where(
                        section.deleteFlag.isFalse(),
                        likeLocation(search.getLocation())
                )
                .fetchOne();
    }

    public JPAQuery<SectionListResponse> searchAll(SearchSectionList search) {
        return jpaQueryFactory
                .select(Projections.bean(SectionListResponse.class,
                        section.sectionId,
                        section.sectionName,
                        section.location,
                        inventory.quantity,
                        warehouse.id.as("warehouseId"),
                        warehouse.warehouseName,
                        section.productId,
                        employee.name,
                        section.createdDate
                        ))
                .from(section)
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(section.createdBy))
                .leftJoin(warehouse)
                .on(warehouse.id.eq(section.warehouseId))
                .leftJoin(inventory)
                .on(inventory.sectionId.eq(section.sectionId))
                .where(
                        section.deleteFlag.isFalse(),
                        likeLocation(search.getLocation()),
                        likeSectionName(search.getSectionName())
                );
    }

    private BooleanExpression likeLocation(String location){
        return StringUtils.isNotEmpty(location) ? section.location.like("%" + location + "%") : null;
    }

    private BooleanExpression likeSectionName(String sectionName){
        return StringUtils.isNotEmpty(sectionName) ? section.sectionName.like("%" + sectionName + "%") : null;
    }

}
