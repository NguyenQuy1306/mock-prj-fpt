package com.curcus.lms.model.mapper;

import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OthersMapper {

    // Category
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "categoryName", target = "categoryName")
    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponseList(List<CategoryResponse> categoryResponses);
}
