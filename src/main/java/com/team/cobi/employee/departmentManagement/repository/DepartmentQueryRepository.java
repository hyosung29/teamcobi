package com.team.cobi.employee.departmentManagement.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.employee.departmentManagement.dto.DepartmentCodeResponse;
import com.team.cobi.employee.departmentManagement.dto.DepartmentListResponse;
import com.team.cobi.employee.departmentManagement.dto.SearchDepartmentList;
import com.team.cobi.employee.departmentManagement.entity.Department;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.employee.departmentManagement.entity.QDepartment.department;
import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;

@Repository
public class DepartmentQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;


    public DepartmentQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Department.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<DepartmentListResponse> searchPage(SearchDepartmentList search, Pageable pageable) {
        JPAQuery<DepartmentListResponse> list = searchAll(search);
        // 아래에 입력한 searchAll 쿼리문을 applyPagination 함수가 페이징처리 및 리밋 오프셋 솔트(정렬) 기능을 해줌. .fetch는 조회라는 뜻
        List<DepartmentListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = searchTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Long searchTotalCount(SearchDepartmentList search) {
        // 게시글 Total 수 가져오기
        return jpaQueryFactory
                .select(department.count())
                .from(department)
                .where(
                        //  deleteFlag 가 false 인 것만 조회
                        department.deleteFlag.isFalse(),
                        likeDepartmentName(search.getDepartmentName())
                )
                // 1건만 조회 = count 니까..
                .fetchOne();
    }

    public JPAQuery<DepartmentListResponse> searchAll(SearchDepartmentList search) {
        return jpaQueryFactory
                //
                .select(Projections.bean(DepartmentListResponse.class,
                        department.id,
                        department.departmentName,
                        employee.name,
                        department.createdDate
                ))
                .from(department)
//                .join(department.employee, employee)
                // 작성자가 null 인값도 포함하여 조회
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(department.createdBy))
                .where(
                        department.deleteFlag.isFalse(),
                        likeDepartmentName(search.getDepartmentName())
                );
    }

    // 부서명
    private BooleanExpression likeDepartmentName(String departmentName) {
        return StringUtils.isNotEmpty(departmentName) ? department.departmentName.like("%" + departmentName + "%") : null;
    }
}

