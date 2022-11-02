package com.team.cobi.notice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class NoticeCreateRequest {

    @ApiModelProperty(name = "제목", position = 1)    // 스웨거 때문에 쓴거, 원래는 없어도돼요.
    private String title;

    @ApiModelProperty(name = "내용", position = 2)
    private String content;
}
