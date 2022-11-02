package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WarehouseUpdateRequest {

    @ApiModelProperty("작성자")
    private String createdBy;

    @ApiModelProperty("창고명")
    private String warehouseName;

    @ApiModelProperty("용적률")
    private int volume;

    @ApiModelProperty("창고상태")
    private String status;




}
