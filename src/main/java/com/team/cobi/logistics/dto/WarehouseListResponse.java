package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class WarehouseListResponse {

    private String id;

    private String name;

    @ApiModelProperty("창고명")
    private String warehouseName;

    @ApiModelProperty("전체재고수량")
    private int quantity;

    @ApiModelProperty("용적률")
    private int volume;

    @ApiModelProperty("작성자")
    private String createdBy;

    @ApiModelProperty("상태")
    private String status;
}
