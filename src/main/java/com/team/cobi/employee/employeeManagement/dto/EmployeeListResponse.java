package com.team.cobi.employee.employeeManagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class EmployeeListResponse {

    private String id;

    @ApiModelProperty("사원번호")
    private String employeeNumber;

    @ApiModelProperty(name = "부서ID")
    private String departmentId;

    @ApiModelProperty(name = "부서명")
    private String departmentName;

    @ApiModelProperty(name = "직책")
    private String positionName;

    @ApiModelProperty("이름")
    private String name;

    @ApiModelProperty("등록일자")
    private LocalDateTime createdDate;

    @ApiModelProperty("재직상태")
    private String employmentStatus;

}
