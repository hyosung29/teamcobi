package com.team.cobi.employee.positionManagement.controller;

import com.team.cobi.employee.positionManagement.dto.*;
import com.team.cobi.employee.positionManagement.entity.Position;
import com.team.cobi.employee.positionManagement.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/position")
public class PositionController {

    private final PositionService positionService;

    @PostMapping("")
    public void createPosition(@RequestBody PositionCreateRequest request) {
        positionService.createPosition(request);
    }

    @GetMapping("")
    public ResponseEntity<Page<PositionListResponse>> getPositionList(SearchPositionList search, Pageable pageable) {
        return ResponseEntity.ok().body(positionService.getPositionPage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(positionService.getPositionDetail(id));
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable("id") String id, @RequestBody PositionUpdateRequest request) {
        positionService.updatePosition(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePosition(@PathVariable("id") String id) {
        positionService.deletePosition(id);
    }

    // 사원등록에서 부서명 리스트 출력
    @GetMapping("/codes")
    public List<PositionCodeResponse> getPositionCodes() {
        return positionService.getPositionCodes();
    }
}
