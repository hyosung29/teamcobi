package com.team.cobi.employee.departmentManagement.controller;

import com.team.cobi.employee.departmentManagement.dto.*;
import com.team.cobi.employee.departmentManagement.entity.Department;
import com.team.cobi.employee.departmentManagement.service.DepartmentService;
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
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("")
    public void createDepartment(@RequestBody DepartmentCreateRequest request) {
        departmentService.createDepartment(request);
    }

    @GetMapping("")
    public ResponseEntity<Page<DepartmentListResponse>> getDepartmentList(SearchDepartmentList search, Pageable pageable) {
        return ResponseEntity.ok().body(departmentService.getDepartmentPage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(departmentService.getDepartmentDetail(id));
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable("id") String id, @RequestBody DepartmentUpdateRequest request) {
        departmentService.updateDepartment(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable("id") String id) {
        departmentService.deleteDepartment(id);
    }

    // 사원등록에서 부서명 리스트 출력
    @GetMapping("/codes")
    public List<DepartmentCodeResponse> getDepartmentCodes() {
        return departmentService.getDepartmentCodes();
    }
}
