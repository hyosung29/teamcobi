package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryUpdateRequest {

    @ApiModelProperty(name = "수량")
    private int quantity;

    @ApiModelProperty(name = "상품ID")
    private String productId;

    @ApiModelProperty(name = "창고ID")
    private String warehouseId;

    @ApiModelProperty(name = "섹션ID")
    private String sectionId;
}
