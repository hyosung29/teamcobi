package com.team.cobi.employee.employeeManagement.dto;

import com.team.cobi.employee.employeeManagement.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EmployeeCodeResponse {

    private String id;
    private String employeeNumber;

    public EmployeeCodeResponse(Employee employee) {
        this.id = employee.getId();
        this.employeeNumber = employee.getEmployeeNumber();
    }

}
