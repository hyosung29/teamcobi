package com.team.cobi.sales.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
public class SalesClientUpdateRequest {

    @ApiModelProperty("거래처명")
    private String clientName;

    @ApiModelProperty("거래처코드")
    private int clientNum;

    @ApiModelProperty("거래유형")
    private String type;

    @ApiModelProperty("대표자명")
    private String ceoName;

    @ApiModelProperty("주소")

    private String address;

    @ApiModelProperty("상세주소")
    private String detailAddress;

    @ApiModelProperty("우편번호")
    private int zipCode;

    @ApiModelProperty("이메일")
    private String email;

    @Column(name = "사업자번호")
    private String registerNum;

    @ApiModelProperty("업종")
    private String businessType;

    @ApiModelProperty("전화번호")
    private String telephone;

    @ApiModelProperty("팩스번호")
    private String fax;

    @ApiModelProperty("휴대폰번호")
    private String cellPhone;

}
