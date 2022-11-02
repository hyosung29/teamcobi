package com.team.cobi.purchase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchPurchaseEstimateList {

    @ApiModelProperty("거래처ID")
    private String clientName;


}
