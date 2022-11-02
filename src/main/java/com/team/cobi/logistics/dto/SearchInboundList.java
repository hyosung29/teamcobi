package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Setter
@Getter
public class SearchInboundList {

    @ApiModelProperty("거래처명")
    private String clientName;


}
