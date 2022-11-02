package com.team.cobi.employee.employeeManagement.controller;

import com.team.cobi.employee.employeeManagement.dto.*;
import com.team.cobi.employee.employeeManagement.entity.Employee;
import com.team.cobi.employee.employeeManagement.service.EmployeeService;
import com.team.cobi.sales.dto.SalesClientCodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    
    @PostMapping("")
    public void createEmployee(EmployeeCreateRequest request) {
        employeeService.createEmployee(request);
    }

//    @GetMapping("")
//    public ResponseEntity<List<Employee>> getEmployeeList() {
//        return ResponseEntity.ok().body(noticeService.getEmployeeList());
//    }

    @GetMapping("")
    public ResponseEntity<Page<EmployeeListResponse>> getEmployeeList(SearchEmployeeList search, Pageable pageable) {
        return ResponseEntity.ok().body(employeeService.getEmployeePage(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(employeeService.getEmployeeDetail(id));
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable("id") String id, EmployeeUpdateRequest request) {
        employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") String id) {
        employeeService.deleteEmployee(id);
    }

    // 주문서 등록에서 클라이언트 리스트 출력
    @GetMapping("/codes")
    public List<EmployeeCodeResponse> getEmployeeCodes() {
        return employeeService.getEmployeeCodes();
    }
}
