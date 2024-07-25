package com.curcus.lms.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SectionDetailResponse {
    private String sectionName;
    private Long position;
    private Set<ContentDetailResponse> contents;
}
