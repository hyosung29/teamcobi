package com.team.cobi.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchNoticeList {

    @ApiModelProperty("제목")
    private String title;
}
