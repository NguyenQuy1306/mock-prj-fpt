package com.curcus.lms.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

import com.curcus.lms.model.entity.Course;
import com.curcus.lms.model.entity.Student;

import java.io.Serializable;

@AllArgsConstructor
@Setter
@Getter
public class EnrollmentResponse implements Serializable {
    private Long enrollmentId;
    private Long studentId;
    private Long courseId;
    private Date enrollmentDate;
    private Boolean isComplete;

    @Override
    public String toString() {
        return "EnrollmentResponse{" +
                "enrollmentId=" + enrollmentId +
                ", studentId='" + studentId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", enrollmentDate='" + enrollmentDate + '\'' +
                ", isComplete='" + isComplete + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EnrollmentResponse that = (EnrollmentResponse) o;
        return Objects.equals(enrollmentId, that.enrollmentId) && Objects.equals(studentId, that.studentId)
                && Objects.equals(courseId, that.courseId) && Objects.equals(enrollmentDate, that.enrollmentDate)
                && Objects.equals(isComplete, that.isComplete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId, studentId, courseId, enrollmentDate, isComplete);
    }
}
