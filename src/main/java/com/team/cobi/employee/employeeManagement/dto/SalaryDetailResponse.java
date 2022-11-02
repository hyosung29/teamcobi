package com.team.cobi.employee.employeeManagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class SalaryDetailResponse {

    @ApiModelProperty("급여ID")
    private String id;

    @ApiModelProperty(name = "이름")
    private String name;

    @ApiModelProperty(name = "부서ID")
    private String departmentId;

    @ApiModelProperty(name = "부서명")
    private String departmentName;

    @ApiModelProperty(name = "직책")
    private String positionName;

    @ApiModelProperty(name = "사원번호")
    private String employeeNumber;

    @ApiModelProperty(name = "기본급")
    private int basicPay;

    @ApiModelProperty(name = "시간외수당")
    private int overtimePay;

    @ApiModelProperty(name = "상여금")
    private int bonusPay;

    @ApiModelProperty(name = "식대")
    private int mealAllowance;

    @ApiModelProperty(name = "소득세")
    private int incomeTax;

    @ApiModelProperty(name = "실급여액")
    private int netSalary;

    @ApiModelProperty(name = "급여지급일")
    private Date payDay;

    @ApiModelProperty(name = "급여지급상태")
    private String paymentStatus;

    @ApiModelProperty(name = "사원ID")
    private String employeeId;

    @ApiModelProperty("등록자")
    private String createdBy;

    @ApiModelProperty("등록일자")
    private LocalDateTime createdDate;
}
