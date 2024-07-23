package com.curcus.lms.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseSearchResponse implements Serializable {
    private Long courseId;
    private String courseThumbnail;
    private String title;

    private Double avgRating;

    private Long totalReviews;

    private String categoryName;

    private Long prePrice;

    private Long aftPrice;


    @Override
    public String toString() {
        return "CourseSearchResponse{" +
                "courseId=" + courseId +
                ", courseThumbnail='" + courseThumbnail + '\'' +
                ", title='" + title + '\'' +
                ", avgRating=" + avgRating +
                ", totalReviews=" + totalReviews +
                ", categoryName='" + categoryName + '\'' +
                ", prePrice=" + prePrice +
                ", aftPrice=" + aftPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CourseSearchResponse that = (CourseSearchResponse) o;
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(courseThumbnail, that.courseThumbnail) &&
                Objects.equals(title, that.title) &&
                Objects.equals(avgRating, that.avgRating) &&
                Objects.equals(totalReviews, that.totalReviews) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(prePrice, that.prePrice) &&
                Objects.equals(aftPrice, that.aftPrice);
    }

    @Override
    public int hashCode() {return Objects.hash(
            courseId, courseThumbnail, title, avgRating,
            totalReviews, categoryName, prePrice, aftPrice
    );}
}
