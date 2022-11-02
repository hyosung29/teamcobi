package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchInventoryList {

    @ApiModelProperty("상품명")
    private String productName;
}
