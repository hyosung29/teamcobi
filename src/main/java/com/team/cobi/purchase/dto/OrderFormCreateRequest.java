package com.team.cobi.purchase.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
/**
 * Create 객체는 Spring validation 써서 객체 맵핑시에 처리할 수 있도록
 *
 */
public class OrderFormCreateRequest {
    // 구매 주문서를 생성(insert) 할 때 필요한 값들을 dto에 담을게여

    @ApiModelProperty(name = "거래유형")
    private String type;

    @ApiModelProperty(name = "견적서ID")
    private String id;

    @ApiModelProperty(name = "요청일자")
    private LocalDateTime createdDate;

    @ApiModelProperty(name = "구매확정일자")
    private LocalDateTime endDate;

    @ApiModelProperty(name = "요청상태")
    private String status;
}
