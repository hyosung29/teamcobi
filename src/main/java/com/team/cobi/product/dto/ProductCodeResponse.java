package com.team.cobi.product.dto;

import com.team.cobi.product.entity.Product;
import com.team.cobi.sales.entity.Client;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductCodeResponse {

    private String id;

    @ApiModelProperty("품명")
    private String productName;

    private int unitPrice;

    public ProductCodeResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.unitPrice = product.getUnitPrice();
    }
}
