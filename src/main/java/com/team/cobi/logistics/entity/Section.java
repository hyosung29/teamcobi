package com.team.cobi.logistics.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.logistics.dto.SectionCreateRequest;
import com.team.cobi.logistics.dto.SectionUpdateRequest;
import com.team.cobi.purchase.dto.PurchaseEstimateCreateRequest;
import com.team.cobi.purchase.dto.PurchaseEstimateUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rack")
public class Section extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String sectionId;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @Column(name = "location")
    private String location; //-품명

    @Column(name = "quantity")
    private int quantity; //-수량

    @Column(name = "product_id")
    private String productId;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "name")
    private String sectionName;



    public Section(SectionCreateRequest request) {
        this.location = request.getLocation();
        this.quantity = request.getQuantity();
        this.warehouseId = request.getWarehouseId();
        this.productId = request.getProductId();
        this.sectionName = request.getSectionName();
    }

    public void update(SectionUpdateRequest request) {
        this.location = request.getLocation();
        this.quantity = request.getQuantity();
        this.warehouseId = request.getWarehouseId();
        this.productId = request.getProductId();
        this.sectionName = request.getSectionName();
    }

    public void delete() {
        this.deleteFlag = true;
    }
}
