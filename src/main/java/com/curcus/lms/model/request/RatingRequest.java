package com.curcus.lms.model.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequest {
    @NotNull(message = "Student Id is mandatory")
    private Long studentId;
    @NotNull(message = "Course Id is mandatory")
    private Long courseId;
    @NotNull
    @Max(value = 10, message = "Rating cannot be greater than 10")
    @Min(value = 0, message = "Rating cannot be less than 10")
    private Long rating;

    private String comment;
}
