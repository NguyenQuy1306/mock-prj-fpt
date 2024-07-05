package com.curcus.lms.model.request;

import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

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
    
    @NotNull(message = "Course video must not be null")
    private MultipartFile courseVideo;
	public VideoContentCreateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VideoContentCreateRequest(Long sectionId, Long courseId, MultipartFile courseVideo) {
		super();
		this.sectionId = sectionId;
		this.courseId = courseId;
		this.courseVideo=courseVideo;
	}

    
}
