package com.team.cobi.employee.positionManagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionUpdateRequest {

    @ApiModelProperty("부서명")
    private String positionName;


}
