package com.team.cobi.employee.departmentManagement.repository;

import com.team.cobi.employee.departmentManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    List<Department> findByDeleteFlagFalse();

}