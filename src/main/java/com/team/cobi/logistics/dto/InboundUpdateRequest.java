package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class InboundUpdateRequest {

    private String warehouseId;

    private String warehouseName;

    @ApiModelProperty(name = "수량", position = 1)
    private int productQuantity;

    @ApiModelProperty(name = "상품ID", position = 2)
    private String productId;

//    @ApiModelProperty(name = "창고ID", position = 3)
//    private String warehouse;

    @ApiModelProperty(name = "섹션ID", position = 4)
    private String sectionId;

}
