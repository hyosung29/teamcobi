package com.team.cobi.logistics.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.logistics.dto.WarehouseCreateRequest;
import com.team.cobi.logistics.dto.WarehouseUpdateRequest;
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
public class Warehouse extends BaseEntity implements Serializable  {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @Column(name = "name")
    private String warehouseName;

    private int volume;

    private String status;



    public Warehouse(WarehouseCreateRequest request) {
        this.warehouseName = request.getWarehouseName();
        this.status = request.getStatus();
        this.volume = request.getVolume();
    }

    public void update(WarehouseUpdateRequest request) {
        this.warehouseName = request.getWarehouseName();
//        this.createdBy = request.getCreatedBy();
        this.volume = request.getVolume();
        this.status = request.getStatus();
    }

    public void delete() { this.deleteFlag = true; }

}
