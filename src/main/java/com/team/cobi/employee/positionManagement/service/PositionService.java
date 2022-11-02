package com.team.cobi.employee.positionManagement.service;

import com.team.cobi.employee.positionManagement.dto.*;
import com.team.cobi.employee.positionManagement.entity.Position;
import com.team.cobi.employee.positionManagement.repository.PositionQueryRepository;
import com.team.cobi.employee.positionManagement.repository.PositionRepository;
import com.team.cobi.util.exception.NullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionQueryRepository positionQueryRepository;

    @Transactional
    public void createPosition(PositionCreateRequest request) {
        positionRepository.save(new Position(request));
    }

    @Transactional(readOnly = true)
    public Page<PositionListResponse> getPositionPage(SearchPositionList search, Pageable pageable) {
        return positionQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public Position getPositionDetail(String id) {
        return getPosition(id);
    }

    @Transactional
    public void updatePosition(String id, PositionUpdateRequest request) {
        Position position = getPosition(id);
        position.update(request);
        positionRepository.save(position);
    }

    @Transactional
    public void deletePosition(String id) {
        Position position = getPosition(id);
        position.delete();
        positionRepository.save(position);
    }

    // 사원등록에서 부서명 리스트 출력
    @Transactional
    public List<PositionCodeResponse> getPositionCodes() {
        List<Position> positionList = positionRepository.findByDeleteFlagFalse();
        return positionList.stream().map(PositionCodeResponse::new).collect(Collectors.toList());
    }



    @Transactional(readOnly = true)
    public Position getPosition(String id) {
        Position position = positionRepository.findById(id).orElseThrow(NullException::new);
//        if (position == null) throw new NullException();
        return position;
    }

}
