package com.team.cobi.employee.employeeManagement.service;

import com.team.cobi.employee.employeeManagement.dto.SalaryDetailResponse;
import com.team.cobi.employee.employeeManagement.dto.SearchSalaryList;
import com.team.cobi.employee.employeeManagement.entity.Salary;
import com.team.cobi.employee.employeeManagement.repository.SalaryQueryRepository;
import com.team.cobi.employee.employeeManagement.repository.SalaryRepository;
import com.team.cobi.employee.employeeManagement.dto.SalaryCreateRequest;
import com.team.cobi.employee.employeeManagement.dto.SalaryUpdateRequest;
import com.team.cobi.util.exception.NullException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final SalaryQueryRepository salaryQueryRepository;

    @Transactional
    public void createSalary(SalaryCreateRequest request) {
        salaryRepository.save(new Salary(request));
    }


    @Transactional(readOnly = true)
    public Page<SalaryDetailResponse> getSalaryPage(SearchSalaryList search, Pageable pageable) {
        return salaryQueryRepository.searchPage(search, pageable);
    }

    @Transactional(readOnly = true)
    public SalaryDetailResponse getSalaryDetail(String id) {
        return salaryQueryRepository.getSalaryDetail(id);
    }

    @Transactional
    public void updateSalary(String id, SalaryUpdateRequest request) {
        Salary salary = getSalary(id);
        salary.update(request);
        salaryRepository.save(salary);
    }

    @Transactional
    public void deleteSalary(String id) {
        Salary salary = getSalary(id);
        salary.delete();
        salaryRepository.save(salary);
    }

    @Transactional()
    public Salary getSalary(String id) {
        Salary salary = salaryRepository.findById(id).orElse(null);
        if (salary == null) throw new NullException();
        return salary;
    }

}
