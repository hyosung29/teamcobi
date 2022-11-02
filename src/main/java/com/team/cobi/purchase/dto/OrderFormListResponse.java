package com.team.cobi.purchase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString

public class OrderFormListResponse {

    @ApiModelProperty(name = "발주코드")
    private String id;

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
    private LocalDateTime modifiedDate;

    @ApiModelProperty(name = "구매확정일자")
    private LocalDateTime endDate;

    @ApiModelProperty("등록자")
    private String createdBy;

    @ApiModelProperty(name = "창고이름")
    private String warehouseName;

    @ApiModelProperty(name = " 섹션이름")
    private String sectionName;
}
