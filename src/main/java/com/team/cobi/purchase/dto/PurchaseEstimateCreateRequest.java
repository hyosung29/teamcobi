package com.team.cobi.purchase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class PurchaseEstimateCreateRequest {

    @ApiModelProperty(name = "상품ID"      )
    private String productId; //-품명

    @ApiModelProperty(name = "거래처ID", position = 2)
    private String clientId; //-거래처 이름 (FK)

    @ApiModelProperty(name = "수량", position = 3)
    private int productQuantity; //-수량

    @ApiModelProperty(name = "단가", position = 4)
    private int unitPrice; //-단가

    @ApiModelProperty(name = "공급가액", position = 5)
    private int supplyValue; //-공급가액

    @ApiModelProperty(name ="세액", position = 6)
    private int tax; //-세액

    @ApiModelProperty(name = "총금액", position = 7)
    private int totalPrice; //-총금액

    @ApiModelProperty(name = "견적서상태", position = 8)
    private String status; //-견적서상태

    @ApiModelProperty(name = "타입", position = 9)
    private String type;

}
