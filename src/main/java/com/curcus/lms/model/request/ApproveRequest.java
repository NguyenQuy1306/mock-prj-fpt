package com.curcus.lms.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApproveRequest {
    @NotNull(message = "courseId is mandatory")
    private Long courseId;
    @NotNull(message = "approved is mandatory")
    private Boolean approved;
}
