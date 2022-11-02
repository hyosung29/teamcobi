package com.team.cobi.logistics.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class InboundListResponse {

    private String id;

    private Date inboundDate;

    private String status;

    private String orderFormId;

    private String warehouseId;

    private String warehouseName;

    private String sectionId;

    private String sectionName;

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
