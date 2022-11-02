package com.team.cobi.purchase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFormUpdateRequest {
    // 구매주문서를 수정할 때(아마도 내용을 수정하거나 타입을 수정할 때 쓰이겠져) 필요한 값들을 받아올게용

    @ApiModelProperty(name = "요청상태")
    private String status;

}
