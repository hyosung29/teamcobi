package com.team.cobi.employee.positionManagement.dto;

import com.team.cobi.employee.positionManagement.entity.Position;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PositionCodeResponse {

    private String id;

    @ApiModelProperty("직급명")
    private String positionName;

    public PositionCodeResponse(Position position) {
        this.id = position.getId();
        this.positionName = position.getPositionName();
    }
}
