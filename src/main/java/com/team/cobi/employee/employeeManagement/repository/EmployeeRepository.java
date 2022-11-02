package com.team.cobi.employee.employeeManagement.repository;

import com.team.cobi.employee.employeeManagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByDeleteFlagFalseAndEmployeeNumber(String employeeNumber);

    int countByEmployeeNumberLike(String employeeNumber);

    Page<Employee> findByDeleteFlagFalse(Pageable pageable);
    List<Employee> findByDeleteFlagFalseOrderByEmployeeNumber();
}