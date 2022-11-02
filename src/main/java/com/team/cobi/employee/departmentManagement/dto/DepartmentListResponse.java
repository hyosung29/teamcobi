package com.team.cobi.employee.departmentManagement.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class DepartmentListResponse {

    private String id;

    private String departmentName;

    private String name;

    private LocalDateTime createdDate;
}
