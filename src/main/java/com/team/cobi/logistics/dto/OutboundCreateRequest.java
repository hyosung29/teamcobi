package com.team.cobi.logistics.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OutboundCreateRequest {

    private Date OutboundDate;

    private String status;

    private String orderFormId;

    private String warehouseId;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
