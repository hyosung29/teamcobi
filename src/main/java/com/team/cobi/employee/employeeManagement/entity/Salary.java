package com.team.cobi.employee.employeeManagement.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.employee.employeeManagement.dto.SalaryCreateRequest;
import com.team.cobi.employee.employeeManagement.dto.SalaryUpdateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity(name="salary")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Salary extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @Column(name = "basic_pay")
    private int basicPay;

    @Column(name = "overtime_pay")
    private int overtimePay;

    @Column(name = "bonus_pay")
    private int bonusPay;

    @Column(name = "meal_allowance")
    private int mealAllowance;

    @Column(name = "income_tax")
    private int incomeTax;

    @Column(name = "pay_day")
    private Date payDay;

    @Column(name = "net_salary")
    private int netSalary;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "employee_id")
    private String employeeId;
    

    public Salary(SalaryCreateRequest request) {
        this.employeeId = request.getEmployeeId();
        this.basicPay = request.getBasicPay();
        this.overtimePay = request.getOvertimePay();
        this.bonusPay = request.getBonusPay();
        this.mealAllowance = request.getMealAllowance();
        this.incomeTax = request.getIncomeTax();
        this.netSalary = request.getNetSalary();
        this.payDay = request.getPayDay();
        this.paymentStatus = request.getPaymentStatus();
    }

    public void update(SalaryUpdateRequest request) {
        this.basicPay = request.getBasicPay();
        this.overtimePay = request.getOvertimePay();
        this.bonusPay = request.getBonusPay();
        this.mealAllowance = request.getMealAllowance();
        this.incomeTax = request.getIncomeTax();
        this.netSalary = request.getNetSalary();
        this.payDay = request.getPayDay();
        this.paymentStatus = request.getPaymentStatus();
    }

    public void delete() { this.deleteFlag = true; }
}
