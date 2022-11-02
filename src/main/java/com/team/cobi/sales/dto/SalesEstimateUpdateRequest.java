package com.team.cobi.sales.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SalesEstimateUpdateRequest {
    @ApiModelProperty(name = "상품ID")
    private String productId;

    @ApiModelProperty(name = "수량")
    private int productQuantity;

    @ApiModelProperty(name = "단가")
    private int unitPrice;

    @ApiModelProperty(name = "공급가액")
    private int supplyValue;

    @ApiModelProperty(name ="세액")
    private int tax;

    @ApiModelProperty(name = "총금액")
    private int totalPrice;

    @ApiModelProperty(name = "거래처ID")
    private String clientId;

    @ApiModelProperty(name = "견적서상태")
    private String status; //-견적서상태

    @ApiModelProperty(name = "타입")
    private String type;

    @ApiModelProperty("창고명")
    private String warehouseId;

    @ApiModelProperty("섹션명")
    private String sectionId;

    @ApiModelProperty("재고ID")
    private String inventoryId;
}
