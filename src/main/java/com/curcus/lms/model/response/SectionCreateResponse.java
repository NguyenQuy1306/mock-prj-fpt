package com.curcus.lms.model.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class SectionCreateResponse implements Serializable {
	private Long sectionId;
    private Long courseId;
    private String sectionName;
}
