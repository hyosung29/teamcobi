package com.team.cobi.employee.employeeManagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class EmployeeCreateRequest {

    @ApiModelProperty("이름")
    private String name;

    @ApiModelProperty("사진")
    private MultipartFile photo;

    @ApiModelProperty("주민등록번호")
    private String residentRegNum;

    @ApiModelProperty("핸드폰번호")
    private String phone;

    @ApiModelProperty("퇴사일")
    private String resignationDate;

    @ApiModelProperty("주소")
    private String address;

    @ApiModelProperty("상세주소")
    private String detailAddress;

    @ApiModelProperty("부서코드")
    private String departmentId;

    @ApiModelProperty("직급코드")
    private String positionId;

    @ApiModelProperty("재직상태")
    private String employmentStatus;
}
