package com.team.cobi.employee.employeeManagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SalaryUpdateRequest {

    @ApiModelProperty(name = "사원번호")
    private int employeeNumber;

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
}
