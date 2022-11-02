package com.team.cobi.product.entity;

import com.team.cobi.base.BaseEntity;
import com.team.cobi.product.dto.ProductCreateRequest;
import com.team.cobi.product.dto.ProductUpdateRequest;
import com.team.cobi.sales.dto.SalesClientCreateRequest;
import com.team.cobi.sales.dto.SalesClientUpdateRequest;
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

@Entity(name="product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Type(type = "true_false")
    @Column(name = "delete_flag")
    private boolean deleteFlag = false;

    @Column(name = "name")
    private String productName; //-품명

    @Column(name = "category")
    private String category;   // 카테고리

    @Column(name = "unit_price")
    private int unitPrice; //-단가

    public Product(ProductCreateRequest request) {
        this.productName = request.getProductName();
        this.category = request.getCategory();
        this.unitPrice = request.getUnitPrice();
    }

    public void update(ProductUpdateRequest request) {
        this.productName = request.getProductName();
        this.category = request.getCategory();
        this.unitPrice = request.getUnitPrice();
    }

    public void delete() {
        this.deleteFlag = true;
    }
}
