package com.curcus.lms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "enrollments")
public class Enrollment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long enrollmentId;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "userId")
    private Student studentId;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    private Course CourseId;
    @Column(nullable = false)
    private Date enrollmentDate;
    @Column(nullable = false)
    private Boolean isComplete ;

}
