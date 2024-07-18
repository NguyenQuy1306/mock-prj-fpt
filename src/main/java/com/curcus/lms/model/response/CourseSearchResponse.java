package com.curcus.lms.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
public class CourseSearchResponse implements Serializable {
    private Long courseId;
    private String courseThumbnail;
    private String title;
    private String description;
    private Long price;

    private Double avgRating;

    private InstructorPublicResponse instructor;

    private Long totalReviews;

    private String categoryName;

    private Long prePrice;

    private Long aftPrice;

    public CourseSearchResponse() {
        this.prePrice = price; // default value
        this.aftPrice = price; // default value
    }

    @Override
    public String toString() {
        return "CourseSearchResponse{" +
                "courseId=" + courseId +
                ", courseThumbnail='" + courseThumbnail + '\'' +
                ", title='" + title + '\'' +
                ", description=" + description +
                ", price=" + price +
                ", instructor=" + instructor +
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
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(instructor, that.instructor) &&
                Objects.equals(avgRating, that.avgRating) &&
                Objects.equals(totalReviews, that.totalReviews) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(prePrice, that.prePrice) &&
                Objects.equals(aftPrice, that.aftPrice);
    }

    @Override
    public int hashCode() {return Objects.hash(
            courseId, courseThumbnail, title,
            description, price, instructor, avgRating,
            totalReviews, categoryName, prePrice, aftPrice
    );}
}
