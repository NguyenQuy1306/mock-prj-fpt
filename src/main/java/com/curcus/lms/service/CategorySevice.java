package com.curcus.lms.service;

import com.curcus.lms.model.request.CategoryRequest;
import com.curcus.lms.model.response.CategoryResponse;

public interface CategorySevice {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
}
