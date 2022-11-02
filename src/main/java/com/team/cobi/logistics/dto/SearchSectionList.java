package com.team.cobi.logistics.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchSectionList {
    @ApiModelProperty("로케이션")
    private String location;
    
    @ApiModelProperty("섹션명")
    private String sectionName;
}
