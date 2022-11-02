package com.team.cobi.purchase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString

public class OrderFormDetailResponse {

    @ApiModelProperty(name = "발주코드")
    private String id;

    @ApiModelProperty(name = "발주코드")
    private String purchaseNumber;

    @ApiModelProperty(name = "주문번호")
    private String salesNumber;

    @ApiModelProperty(name = "견적서ID")
    private String estimateId;

    @ApiModelProperty(name = "요청타입")
    private String type;

    @ApiModelProperty(name = "거래처ID")
    private String clientId;

    @ApiModelProperty(name = "거래처명")
    private String clientName;

    @ApiModelProperty("품명")
    private String productName;

    @ApiModelProperty("수량")
    private int productQuantity;

    @ApiModelProperty("총금액")
    private int totalPrice;

    @ApiModelProperty(name = "요청상태")
    private String status;

    @ApiModelProperty("등록일자")
    private LocalDateTime createdDate;

    @ApiModelProperty("등록자")
    private String createdBy;

}
