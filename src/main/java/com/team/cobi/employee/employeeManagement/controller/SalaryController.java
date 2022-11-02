package com.team.cobi.employee.employeeManagement.controller;

import com.team.cobi.employee.employeeManagement.dto.SalaryDetailResponse;
import com.team.cobi.employee.employeeManagement.dto.SearchSalaryList;
import com.team.cobi.employee.employeeManagement.service.SalaryService;
import com.team.cobi.employee.employeeManagement.dto.SalaryCreateRequest;
import com.team.cobi.employee.employeeManagement.dto.SalaryUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/salary")
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping("")
    public void createSalary(@RequestBody SalaryCreateRequest request) {
        salaryService.createSalary(request);
    }

//    @GetMapping("")
//    public ResponseEntity<List<salary>> getSalaryList() {
//        return ResponseEntity.ok().body(salaryService.getSalaryList());
//    }

    @GetMapping("")
    public ResponseEntity<Page<SalaryDetailResponse>> getSalaryList(SearchSalaryList search, Pageable pageable) {
        return ResponseEntity.ok().body(salaryService.getSalaryPage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaryDetailResponse> getSalaryDetail(@PathVariable String id) {
        return ResponseEntity.ok(salaryService.getSalaryDetail(id));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Salary> getSalaryDetail(@PathVariable String id) {
//        return ResponseEntity.ok().body(salaryService.getSalaryDetail(id));
//    }

    @PutMapping("/{id}")
    public void updateSalary(@PathVariable("id") String id, @RequestBody SalaryUpdateRequest request) {
        salaryService.updateSalary(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSalary(@PathVariable("id") String id) {
        salaryService.deleteSalary(id);
    }

}


