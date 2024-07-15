package com.curcus.lms.model.mapper;

import com.curcus.lms.model.entity.Category;
import com.curcus.lms.model.entity.Rating;
import com.curcus.lms.model.response.CategoryResponse;
import com.curcus.lms.model.response.RatingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OthersMapper {

    // Category
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "categoryName", target = "categoryName")
    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponseList(List<Category> categories);

    // Rating
    @Mapping(source = "ratingId", target = "ratingId")
    @Mapping(source = "studentId", target = "studentId")
    @Mapping(source = "courseId", target = "courseId")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "comment", target = "comment")
    RatingResponse toRatingResponse(Rating rating);

    List<RatingResponse> toRatingResponseList(List<Rating> ratings);
}
