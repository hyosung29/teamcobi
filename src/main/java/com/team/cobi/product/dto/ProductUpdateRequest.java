package com.team.cobi.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
public class ProductUpdateRequest {

    @ApiModelProperty("품명")
    private String productName;

    @ApiModelProperty("카테고리")
    private String category;

    @ApiModelProperty("단가")
    private int unitPrice;

    @ApiModelProperty("공급가")
    private int supplyValue;
}
