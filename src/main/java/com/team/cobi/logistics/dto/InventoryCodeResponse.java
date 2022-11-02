package com.team.cobi.logistics.dto;

import com.team.cobi.logistics.entity.Inventory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InventoryCodeResponse {

    private String id;

    private String warehouseId;

    private String warehouseName;

    private String sectionName;

    private String sectionId;

    private String productId;

    private String productName;

    private int quantity;

    private int unitPrice;

    public InventoryCodeResponse(Inventory inventory) {
        this.id = inventory.getId();
        this.warehouseId = inventory.getWarehouseId();
        this.sectionId = inventory.getSectionId();
        this.productId = inventory.getProductId();
        this.quantity = inventory.getQuantity();
    }





}
