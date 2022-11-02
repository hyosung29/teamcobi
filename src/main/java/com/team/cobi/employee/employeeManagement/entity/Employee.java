package com.team.cobi.employee.employeeManagement.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.employee.employeeManagement.dto.EmployeeUpdateRequest;
import com.team.cobi.util.exception.UtilClass;
import io.swagger.annotations.ApiModelProperty;
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

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Employee extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @ApiModelProperty("암호")
    @Column(name = "password")
    private String password;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @ApiModelProperty("사원번호")
    @Column(name = "employee_number")
    private String employeeNumber;

    @ApiModelProperty("이름")
    @Column(name = "name")
    private String name;

    @ApiModelProperty("사진")
    @Column(name = "photo")
    private String photo;


    @ApiModelProperty("주민등록번호")
    @Column(name = "resident_reg_num")
    private String residentRegNum;

    @ApiModelProperty("핸드폰번호")
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty("퇴사일")
    @Column(name = "resignation_date")
    private String resignationDate;

    @ApiModelProperty("주소")
    @Column(name = "address")
    private String address;

    @ApiModelProperty("상세주소")
    @Column(name = "detail_address")
    private String detailAddress;

    @ApiModelProperty("부서코드")
    @Column(name = "department_id")
    private String departmentId;

    @ApiModelProperty("직급코드")
    @Column(name = "position_id")
    private String positionId;

    @ApiModelProperty("이메일인증여부")
    @Column(name = "enabled")
    private int enabled;

    @ApiModelProperty("권한")
    @Column(name = "authorities")
    private String authorities = "ROLE_USER";

    @ApiModelProperty("재직상태")
    @Column(name = "employment_status")
    private String employmentStatus;

    public Employee(String name, String photo, String residentRegNum, String phone, String address, String detailAddress, String departmentId, String positionId, String employeeNumber, String password, String employmentStatus) {
        this.employeeNumber = employeeNumber;
        this.password = password;
        this.name = name;
        this.photo = photo;
        this.residentRegNum = residentRegNum;
        this.phone = phone;
        this.address = address;
        this.detailAddress = detailAddress;
        this.departmentId = departmentId;
        this.positionId = positionId;
        this.employmentStatus = employmentStatus;
    }

    public void update(EmployeeUpdateRequest request, String photo) {
        this.name = request.getName();
        this.photo = photo;
        this.residentRegNum = request.getResidentRegNum();
        this.phone = request.getPhone();
        this.address = request.getAddress();
        this.detailAddress = request.getDetailAddress();
        this.departmentId = request.getDepartmentId();
        this.positionId = request.getPositionId();
        this.employmentStatus = request.getEmploymentStatus();
    }

    public void delete() {
        this.deleteFlag = true;
    }
}
