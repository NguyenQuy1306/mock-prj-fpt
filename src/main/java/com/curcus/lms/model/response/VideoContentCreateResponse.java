package com.curcus.lms.model.response;

import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data

public class VideoContentCreateResponse {

    private Long sectionId;


    private Long courseId;


    private String videoType;
    

    private String videoUrl;
	public VideoContentCreateResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoContentCreateResponse(Long sectionId, Long courseId, String videoType, String videoUrl) {
		super();
		this.sectionId = sectionId;
		this.courseId = courseId;
		this.videoType = videoType;
		this.videoUrl = videoUrl;
	}

    
}
