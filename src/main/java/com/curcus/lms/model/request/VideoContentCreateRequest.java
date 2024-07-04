package com.curcus.lms.model.request;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class VideoContentCreateRequest {
	@NotNull(message = "Section ID cannot be null")
    private Long sectionId;

    @NotNull(message = "Course ID cannot be null")
    private Long courseId;

    @NotBlank(message = "Video type cannot be blank")
    private String videoType;

    @URL(message = "Invalid video URL")
    @NotBlank(message = "Video URL cannot be blank")
    private String videoUrl;
	public VideoContentCreateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoContentCreateRequest(Long sectionId, Long courseId, String videoType, String videoUrl) {
		super();
		this.sectionId = sectionId;
		this.courseId = courseId;
		this.videoType = videoType;
		this.videoUrl = videoUrl;
	}

    
}
