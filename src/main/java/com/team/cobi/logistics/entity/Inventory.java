package com.team.cobi.logistics.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.logistics.dto.InboundUpdateRequest;
import com.team.cobi.logistics.dto.InventoryCreateRequest;
import com.team.cobi.logistics.dto.InventoryUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "section_id")
    private String sectionId;

    public Inventory(InboundUpdateRequest request) {
        this.quantity = request.getProductQuantity();
        this.productId = request.getProductId();
        this.warehouseId = request.getWarehouseId();
        this.sectionId = request.getSectionId();
    }

    public void update(int quantity, String productId, String warehouseId, String sectionId) {
        this.quantity = this.quantity + quantity;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.sectionId = sectionId;
    }

    public void outboundInventory(int quantity) {
        this.quantity = this.quantity - quantity;
    }

    public void delete() {
        this.deleteFlag = true;
    }
}
