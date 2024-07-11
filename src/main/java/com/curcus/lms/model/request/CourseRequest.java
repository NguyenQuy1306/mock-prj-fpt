package com.curcus.lms.model.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest implements Serializable {

    private Long courseId;

    private String title;

    private String description;

    private Long price;

    private Boolean isFree;

    private Long instructorId;

    private int categoryId;

}
