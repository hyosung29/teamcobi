package com.team.cobi.purchase.entity;

import com.team.cobi.base.BaseEntity;
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
public class PurchaseEstimate extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id; //-견적서 번호

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @Column(name = "product_id")
    private String productId; //-품명

    @Column(name = "product_quantity")
    private int productQuantity; //-수량

    @Column(name = "unit_price")
    private int unitPrice; //-단가

    @Column(name = "supply_value")
    private int supplyValue; //-공급가액

    @Column(name = "tax")
    private int tax; //-세액

    @Column(name = "total_price")
    private int totalPrice; //-총금액

    @Column(name = "client_id")
    private String clientId; //-거래처 이름 (FK)


    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;


    public PurchaseEstimate(PurchaseEstimateCreateRequest request) {
        this.productId = request.getProductId();
        this.productQuantity = request.getProductQuantity();
        this.unitPrice = request.getUnitPrice();
        this.supplyValue = request.getSupplyValue();
        this.tax = request.getTax();
        this.totalPrice = request.getTotalPrice();
        this.clientId = request.getClientId();
        this.status = request.getStatus();
        this.type = request.getType();
    }

    public void update(PurchaseEstimateUpdateRequest request) {
        this.productId = request.getProductId();
        this.productQuantity = request.getProductQuantity();
        this.unitPrice = request.getUnitPrice();
        this.supplyValue = request.getSupplyValue();
        this.tax = request.getTax();
        this.totalPrice = request.getTotalPrice();
        this.clientId = request.getClientId();
        this.status = request.getStatus();
        this.type = request.getType();
    }

    public void updateStatus() {
        this.status = "승인완료";
    }


    public void delete() {
        this.deleteFlag = true;
    }

}
