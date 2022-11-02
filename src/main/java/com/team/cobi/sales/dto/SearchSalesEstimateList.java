package com.team.cobi.sales.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchSalesEstimateList {
    @ApiModelProperty("거래처명")
    private String clientName;
}
