package com.team.cobi.logistics.dto;

import lombok.Data;

@Data
public class WarehouseCreateRequest {

    private String warehouseName;

    private String status;

    private int volume;

}
