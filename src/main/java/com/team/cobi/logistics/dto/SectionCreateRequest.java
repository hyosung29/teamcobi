package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SectionCreateRequest {

    @ApiModelProperty(name = "로케이션", position = 1)
    private String location;

    @ApiModelProperty(name = "수량", position = 2)
    private int quantity;

    @ApiModelProperty(name = "창고ID", position = 3)
    private String warehouseId;

    @ApiModelProperty(name = "상품ID", position = 4)
    private String productId;

    @ApiModelProperty(name = "섹션명", position = 5)
    private String sectionName;


}
