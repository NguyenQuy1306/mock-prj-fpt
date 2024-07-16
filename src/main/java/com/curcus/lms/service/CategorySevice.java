package com.curcus.lms.service;

import java.util.List;

import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.request.CategoryRequest;
import com.curcus.lms.model.response.CategoryResponse;

public interface CategorySevice {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    Category findById(int id);
    List<Category> getAllCategory();

}
