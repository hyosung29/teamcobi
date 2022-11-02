package com.team.cobi.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class NoticeUpdateRequest {

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("내용")
    private String content;

}
