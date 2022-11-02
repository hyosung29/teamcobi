package com.team.cobi.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
public class ProductCreateRequest {

    @ApiModelProperty("품명")
    private String productName;

    @ApiModelProperty("카테고리")
    private String category;

    @ApiModelProperty("단가")
    private int unitPrice;

}
