package com.team.cobi.employee.employeeManagement.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.employee.employeeManagement.dto.SalaryDetailResponse;
import com.team.cobi.employee.employeeManagement.dto.SearchSalaryList;
import com.team.cobi.employee.employeeManagement.entity.Salary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.employee.departmentManagement.entity.QDepartment.department;
import static com.team.cobi.employee.positionManagement.entity.QPosition.position;
import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;
import static com.team.cobi.employee.employeeManagement.entity.QSalary.salary;

@Repository
public class SalaryQueryRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public SalaryQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Salary.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<SalaryDetailResponse> searchPage(SearchSalaryList search, Pageable pageable) {
        JPAQuery<SalaryDetailResponse> list = searchAll(search);
        List<SalaryDetailResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = searchTotalCount(search);

        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Long searchTotalCount(SearchSalaryList search) {
        // 게시글 Total 수 가져오기
        return jpaQueryFactory
                .select(salary.count())
                .from(salary)
                .leftJoin(employee)
                .on(salary.employeeId.eq(employee.id))
                .where(
                        //  deleteFlag 가 false 인 것만 조회
                        salary.deleteFlag.isFalse(),
                        likeEmployeeNumber(search.getEmployeeNumber()),
                        likeName(search.getName())
                )
                .fetchOne();
    }

    public SalaryDetailResponse getSalaryDetail(String id) {
        return jpaQueryFactory
                .select(Projections.bean(SalaryDetailResponse.class,
                        salary.id,
                        employee.id.as("employeeId"),
                        employee.employeeNumber,
                        employee.name,
                        department.id.as("departmentId"),
                        department.departmentName,
                        position.id.as("positionId"),
                        position.positionName,
                        salary.basicPay,
                        salary.overtimePay,
                        salary.bonusPay,
                        salary.mealAllowance,
                        salary.incomeTax,
                        salary.netSalary,
                        salary.payDay,
                        salary.paymentStatus
                ))
                .from(salary)
                .leftJoin(employee)
                .on(salary.employeeId.eq(employee.id))
                .leftJoin(department)
                .on(department.id.eq(employee.departmentId))
                .leftJoin(position)
                .on(position.id.eq(employee.positionId))
                .where(
                        employee.deleteFlag.isFalse(),
                        salary.id.eq(id)
                )
                .fetchOne();
    }


    public JPAQuery<SalaryDetailResponse> searchAll(SearchSalaryList search) {
        return jpaQueryFactory
                .select(Projections.bean(SalaryDetailResponse.class,
                        salary.id,
                        employee.id.as("employeeId"),
                        employee.employeeNumber,
                        employee.name,
                        department.id.as("departmentId"),
                        department.departmentName,
                        position.id.as("positionId"),
                        position.positionName,
                        salary.basicPay,
                        salary.overtimePay,
                        salary.bonusPay,
                        salary.mealAllowance,
                        salary.incomeTax,
                        salary.netSalary,
                        salary.payDay,
                        salary.paymentStatus
                ))
                .from(salary)
                .leftJoin(employee)
                .on(salary.employeeId.eq(employee.id))
                .leftJoin(department)
                .on(department.id.eq(employee.departmentId))
                .leftJoin(position)
                .on(position.id.eq(employee.positionId))
                .where(
                        salary.deleteFlag.isFalse(),
                        // 아래 두 메서드는 검색할 때 필요한거라 살려둬야함
                        likeEmployeeNumber(search.getEmployeeNumber()),
                        likeName(search.getName())
                );
    }

    // 사원번호
    private BooleanExpression likeEmployeeNumber(String employeeNumber) {
        return StringUtils.isNotEmpty(employeeNumber) ? employee.employeeNumber.like("%" + employeeNumber + "%") : null;
    }

//    private BooleanExpression likeEmployeeNumber(String employeeNumber) {
//        if (StringUtils.isNotEmpty(employeeNumber)) {
//            return employee.employeeNumber.eq(employeeNumber);
//        }
//        return null;
//    }

    // 사원명
    private BooleanExpression likeName(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return employee.name.like("%" + name + "%");
        }
        return null;
    }
}
