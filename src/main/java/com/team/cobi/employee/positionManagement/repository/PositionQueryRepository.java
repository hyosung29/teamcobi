package com.team.cobi.employee.positionManagement.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.cobi.employee.positionManagement.dto.PositionListResponse;
import com.team.cobi.employee.positionManagement.dto.SearchPositionList;
import com.team.cobi.employee.positionManagement.entity.Position;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.cobi.employee.positionManagement.entity.QPosition.position;
import static com.team.cobi.employee.employeeManagement.entity.QEmployee.employee;

@Repository
public class PositionQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;


    public PositionQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(Position.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Page<PositionListResponse> searchPage(SearchPositionList search, Pageable pageable) {
        JPAQuery<PositionListResponse> list = searchAll(search);
        // 아래에 입력한 searchAll 쿼리문을 applyPagination 함수가 페이징처리 및 리밋 오프셋 솔트(정렬) 기능을 해줌. .fetch는 조회라는 뜻
        List<PositionListResponse> fetch = getQuerydsl().applyPagination(pageable, list).fetch();
        long totalCount = searchTotalCount(search);
        return new PageImpl<>(fetch, pageable, totalCount);
    }

    public Long searchTotalCount(SearchPositionList search) {
        // 게시글 Total 수 가져오기
        return jpaQueryFactory
                .select(position.count())
                .from(position)
                .where(
                        //  deleteFlag 가 false 인 것만 조회
                        position.deleteFlag.isFalse(),
                        likePositionName(search.getPositionName())
                )
                // 1건만 조회 = count 니까..
                .fetchOne();
    }

    public JPAQuery<PositionListResponse> searchAll(SearchPositionList search) {
        return jpaQueryFactory
                //
                .select(Projections.bean(PositionListResponse.class,
                        position.id,
                        position.positionName,
                        employee.name,
                        position.createdDate
                ))
                .from(position)
//                .join(position.employee, employee)
                // 작성자가 null 인값도 포함하여 조회
                .leftJoin(employee)
                .on(employee.employeeNumber.eq(position.createdBy))
                .where(
                        position.deleteFlag.isFalse(),
                        likePositionName(search.getPositionName())
                );
    }

    // 부서명
    private BooleanExpression likePositionName(String positionName) {
        return StringUtils.isNotEmpty(positionName) ? position.positionName.like("%" + positionName + "%") : null;
    }
}

