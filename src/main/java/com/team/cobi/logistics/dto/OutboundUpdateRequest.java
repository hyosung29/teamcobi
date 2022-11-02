package com.team.cobi.logistics.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OutboundUpdateRequest {

    private String id;

    private String warehouseId;

    private String sectionId;

    private String productId;

    private String productName;

    private int productQuantity;


}
