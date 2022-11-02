package com.team.cobi.purchase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchPurchaseOrderFormList {

    @ApiModelProperty(name = "거래처명")
    private String clientName;

    @ApiModelProperty(name = "상품명")
    private String productName;

    @ApiModelProperty(name = "요청상태")
    private String status;

    @ApiModelProperty(name = "요청일자")
    private LocalDateTime createdDate;


}
