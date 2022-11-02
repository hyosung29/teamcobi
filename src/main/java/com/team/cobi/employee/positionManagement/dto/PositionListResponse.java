package com.team.cobi.employee.positionManagement.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class PositionListResponse {

    private String id;

    private String positionName;

    private String name;

    private LocalDateTime createdDate;
}
