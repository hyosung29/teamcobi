package com.team.cobi.logistics.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.logistics.dto.OutboundCreateRequest;
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
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Outbound extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @Column(name = "outbound_date")
    private Date outboundDate;

    private String status;

    @Column(name = "order_form_id")
    private String orderFormId;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "section_id")
    private String sectionId;



    public Outbound(OutboundCreateRequest request) {
        this.status = request.getStatus();
        this.outboundDate = request.getOutboundDate();
        this.orderFormId = request.getOrderFormId();
        this.warehouseId = request.getWarehouseId();
    }

    public void update() {
        this.status = "출고완료";
//        this.orderFormId = request.getRequestId();
//        this.warehouseId = request.getWarehouseId();
    }

    public void delete() { this.deleteFlag = true; }


    public Outbound(String id) {
        this.orderFormId = id;
        this.status = "출고대기";
    }
}
