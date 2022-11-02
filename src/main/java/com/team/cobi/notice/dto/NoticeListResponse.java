package com.team.cobi.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class NoticeListResponse {

    @ApiModelProperty("공지사항ID")
    private String id;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("등록자")
    private String name;

    @ApiModelProperty("등록일자")
    private LocalDateTime createdDate;
}
