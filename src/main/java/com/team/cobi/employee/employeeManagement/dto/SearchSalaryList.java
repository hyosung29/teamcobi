package com.team.cobi.employee.employeeManagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchSalaryList {
    @ApiModelProperty("사원번호")
    private String employeeNumber;

    @ApiModelProperty("이름")
    private String name;
}
