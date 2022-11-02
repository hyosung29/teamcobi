package com.team.cobi.sales.dto;

import com.team.cobi.employee.departmentManagement.entity.Department;
import com.team.cobi.sales.entity.Client;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class SalesClientCodeResponse {

    private String id;

    @ApiModelProperty("거래처명")
    private String clientName;

    @ApiModelProperty(name = "요청타입")
    private String type;

    @ApiModelProperty("품명")
    private String productName;

    @ApiModelProperty("수량")
    private int productQuantity;

    @ApiModelProperty("단가")
    private int unitPrice;

    @ApiModelProperty("총금액")
    private int totalPrice;

    @ApiModelProperty("요청상태")
    private String status;

    @ApiModelProperty("카테고리")
    private String category;

    @ApiModelProperty("등록일자")
    private LocalDateTime createdDate;

    @ApiModelProperty(name = "구매확정일자")
    private LocalDateTime endDate;

    @ApiModelProperty("등록자")
    private String createdBy;


    public SalesClientCodeResponse(Client client) {
        this.id = client.getId();
        this.clientName = client.getClientName();
    }

}