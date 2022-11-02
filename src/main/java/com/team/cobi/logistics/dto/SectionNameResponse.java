package com.team.cobi.logistics.dto;

import com.team.cobi.logistics.entity.Section;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SectionNameResponse {

    private String sectionId;

    @ApiModelProperty("섹션명")
    private String sectionName;

    public SectionNameResponse(Section section) {
        this.sectionId = section.getSectionId();
        this.sectionName = section.getSectionName();
    }
}
