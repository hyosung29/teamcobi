package com.team.cobi.employee.employeeManagement.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.employee.employeeManagement.dto.EmployeeListResponse;
import com.team.cobi.employee.employeeManagement.dto.SearchEmployeeList;
import com.team.cobi.employee.employeeManagement.entity.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.employee.departmentManagement.entity.QDepartment.department;
import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;
import static com.team.cobi.employee.positionManagement.entity.QPosition.position;


@Repository
public class EmployeeQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public EmployeeQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Employee.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<EmployeeListResponse> searchPage(SearchEmployeeList search, Pageable pageable) {
        JPAQuery<EmployeeListResponse> list = searchAll(search);
        List<EmployeeListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = searchTotalCount(search);

        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Long searchTotalCount(SearchEmployeeList search) {
        // 게시글 Total 수 가져오기
        return jpaQueryFactory
                .select(employee.count())
                .from(employee)
                .where(
                        //  deleteFlag가 false 인 것만 조회
                        employee.deleteFlag.isFalse(),
                        likeEmployeeNumber(search.getEmployeeNumber()),
                        likeName(search.getName())
                )
                .fetchOne();
    }

    public JPAQuery<EmployeeListResponse> searchAll(SearchEmployeeList search) {
        return jpaQueryFactory
                .select(Projections.bean(EmployeeListResponse.class,
                        employee.id,
                        employee.employeeNumber,
                        department.id.as("departmentId"),
                        department.departmentName,
                        position.id.as("positionId"),
                        position.positionName,
                        employee.name,
                        employee.employmentStatus,
                        employee.createdDate
                ))
                .from(employee)
                .leftJoin(department)
                .on(department.id.eq(employee.departmentId))
                .leftJoin(position)
                .on(position.id.eq(employee.positionId))
                .where(
                        employee.deleteFlag.isFalse(),
                        likeEmployeeNumber(search.getEmployeeNumber()),
                        likeName(search.getName())
                );
    }

    // 사원번호
    private BooleanExpression likeEmployeeNumber(String employeeNumber) {
        return StringUtils.isNotEmpty(employeeNumber) ? employee.employeeNumber.like("%" + employeeNumber + "%") : null;
    }

    // 사원명
    private BooleanExpression likeName(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return employee.name.like("%" + name + "%");
        }
        return null;
    }
}
