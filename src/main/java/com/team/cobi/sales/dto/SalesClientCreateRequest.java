package com.team.cobi.sales.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class SalesClientCreateRequest {

    @ApiModelProperty("거래유형")
    private String type;

    @ApiModelProperty("거래처명")
    private String clientName;

    @ApiModelProperty("대표자명")
    private String ceoName;

    @Column(name = "사업자번호")
    private String registerNum;

    @ApiModelProperty("주소")
    private String address;

    @ApiModelProperty("상세주소")
    private String detailAddress;

    @ApiModelProperty("우편번호")
    private int zipCode;

    @ApiModelProperty("이메일")
    private String email;

    @ApiModelProperty("업종")
    private String businessType;

    @ApiModelProperty("전화번호")
    private String telephone;

    @ApiModelProperty("팩스번호")
    private String fax;

    @ApiModelProperty("휴대폰번호")
    private String cellPhone;

}
