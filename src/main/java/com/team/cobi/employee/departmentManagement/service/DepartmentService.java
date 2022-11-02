package com.team.cobi.employee.departmentManagement.service;

import com.team.cobi.employee.departmentManagement.dto.*;
import com.team.cobi.employee.departmentManagement.entity.Department;
import com.team.cobi.employee.departmentManagement.repository.DepartmentQueryRepository;
import com.team.cobi.employee.departmentManagement.repository.DepartmentRepository;
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
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentQueryRepository departmentQueryRepository;

    @Transactional
    public void createDepartment(DepartmentCreateRequest request) {
        departmentRepository.save(new Department(request));
    }

    @Transactional(readOnly = true)
    public Page<DepartmentListResponse> getDepartmentPage(SearchDepartmentList search, Pageable pageable) {
        return departmentQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public Department getDepartmentDetail(String id) {
        return getDepartment(id);
    }

    @Transactional
    public void updateDepartment(String id, DepartmentUpdateRequest request) {
        Department department = getDepartment(id);
        department.update(request);
        departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartment(String id) {
        Department department = getDepartment(id);
        department.delete();
        departmentRepository.save(department);
    }

    // 사원등록에서 부서명 리스트 출력
    @Transactional
    public List<DepartmentCodeResponse> getDepartmentCodes() {
        List<Department> departmentList = departmentRepository.findByDeleteFlagFalse();
        return departmentList.stream().map(DepartmentCodeResponse::new).collect(Collectors.toList());
    }



    @Transactional(readOnly = true)
    public Department getDepartment(String id) {
        Department department = departmentRepository.findById(id).orElseThrow(NullException::new);
//        if (department == null) throw new NullException();
        return department;
    }

}
