package com.team.cobi.employee.employeeManagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SalaryCreateRequest {

    @ApiModelProperty(name = "사원ID")
    private String employeeId;

//    @ApiModelProperty(name = "사원번호")
//    private String employeeNumber;

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
