package com.team.cobi.logistics.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.logistics.dto.InboundCreateRequest;
import com.team.cobi.logistics.dto.InboundUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Inbound extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @Column(name = "inbound_date")
    @LastModifiedDate
    private Date inboundDate;

    private String status;

    @Column(name = "order_form_id")
    private String orderFormId;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "section_id")
    private String sectionId;



    public Inbound(InboundCreateRequest request) {
        this.status = "입고대기";
        this.inboundDate = request.getInboundDate();
        this.orderFormId = request.getId();
        this.warehouseId = request.getWarehouseId();
        this.sectionId = request.getSectionId();
    }

    public Inbound(String id) {
        this.orderFormId = id;
        this.status = "입고대기";
    }


    public void update(InboundUpdateRequest request) {
        this.status = "입고완료";
        this.warehouseId = request.getWarehouseId();
        this.sectionId = request.getSectionId();
    }

    public void delete() { this.deleteFlag = true; }

}
