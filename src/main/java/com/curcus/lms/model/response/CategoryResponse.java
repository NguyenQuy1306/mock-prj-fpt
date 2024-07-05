package com.curcus.lms.model.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse implements Serializable {
    private int categoryId;
    private String categoryName;
}
