package com.team.cobi.purchase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseEstimateListResponse {

    private String id;

    @ApiModelProperty("상품ID")
    private String productId;

    @ApiModelProperty("상품명")
    private String productName;

    @ApiModelProperty("수량")
    private int productQuantity;

    @ApiModelProperty("단가")
    private int unitPrice;

    @ApiModelProperty("공급가액")
    private int supplyValue;

    @ApiModelProperty("세액")
    private int tax;

    @ApiModelProperty("총금액")
    private int totalPrice;

    @ApiModelProperty("거래처ID")
    private String clientId;

    @ApiModelProperty(name = "거래처명")
    private String clientName;

    @ApiModelProperty("작성자")
    private String name;

    @ApiModelProperty("등록일자")
    private LocalDateTime createdDate;

    @ApiModelProperty("견적서상태")
    private String status;

    @ApiModelProperty("타입")
    private String type;
}
