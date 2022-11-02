package com.team.cobi.logistics.dto;

import com.team.cobi.employee.departmentManagement.entity.Department;
import com.team.cobi.logistics.entity.Warehouse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseCodeResponse {

    private String id;
    private String warehouseName;

    private int volume;

    private int capacity;

    public WarehouseCodeResponse(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.warehouseName = warehouse.getWarehouseName();
        this.volume = warehouse.getVolume();
    }
}
