package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SectionListResponse {

    private String sectionId;

    @ApiModelProperty(name = "로케이션")
    private String location;

    @ApiModelProperty(name = "수량")
    private int quantity;

    @ApiModelProperty(name = "창고ID")
    private String warehouseId;

    @ApiModelProperty(name = "창고명")
    private String warehouseName;

    @ApiModelProperty(name = "상품ID")
    private String productId;

    @ApiModelProperty("작성자")
    private String name;

    @ApiModelProperty("등록일자")
    private LocalDateTime createdDate;

    @ApiModelProperty("섹션명")
    private String sectionName;
}
