package com.team.cobi.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class ProductListResponse {

    @ApiModelProperty("상품ID")
    private String id;

    @ApiModelProperty("품명")
    private String productName;

    @ApiModelProperty("카테고리")
    private String category;

    @ApiModelProperty("단가")
    private int unitPrice;

    @ApiModelProperty("등록일자")
    private LocalDateTime createdDate;
}


