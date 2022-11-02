package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class InboundCreateRequest {

    private Date inboundDate;

    private String status;

    private String id;

    private String warehouseId;

    private String sectionId;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
