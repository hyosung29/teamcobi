package com.team.cobi.logistics.dto;

import com.team.cobi.logistics.entity.Inventory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InventoryListResponse {

    private String id;

    @ApiModelProperty(name = "수량")
    private int quantity;

    private int totalQuantity;

    @ApiModelProperty(name = "상품ID")
    private String productId;

    @ApiModelProperty(name = "창고ID")
    private String warehouseId;

    @ApiModelProperty(name = "섹션ID")
    private String sectionId;

    @ApiModelProperty("작성자")
    private String name;

    @ApiModelProperty("등록일자")
    private LocalDateTime createdDate;

    private String warehouseName;

    private String sectionName;

    private String productName;


}

