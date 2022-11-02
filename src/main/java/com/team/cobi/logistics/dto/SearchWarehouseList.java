package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchWarehouseList {
    @ApiModelProperty("창고명")
    private String warehouseName;
}
