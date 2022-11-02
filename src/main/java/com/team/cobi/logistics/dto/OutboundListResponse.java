package com.team.cobi.logistics.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class OutboundListResponse {

    private String id;

    private String status;

    private String orderFormId;

    private String warehouseId;

    private String sectionId;

    private String sectionName;

    private String warehouseName;

    public String createdBy;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    // Client
    private String clientId;

    private String clientName;

    //product

    private String productId;

    private String productName;

    private int productQuantity;
}
