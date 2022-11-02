package com.team.cobi.employee.departmentManagement.dto;

import com.team.cobi.employee.departmentManagement.entity.Department;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class DepartmentCodeResponse {

    private String id;

    @ApiModelProperty("부서명")
    private String departmentName;

    public DepartmentCodeResponse(Department department) {
        this.id = department.getId();
        this.departmentName = department.getDepartmentName();
    }
}
