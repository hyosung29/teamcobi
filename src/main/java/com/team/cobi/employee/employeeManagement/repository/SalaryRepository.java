package com.team.cobi.employee.employeeManagement.repository;

import com.team.cobi.employee.employeeManagement.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, String> {

    Page<Salary> findByDeleteFlagFalse(Pageable pageable);

}