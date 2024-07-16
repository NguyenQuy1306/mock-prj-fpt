package com.curcus.lms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    @JoinColumn(name = "student_id", referencedColumnName = "user_Id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    private Course course;
    @Column(nullable = false)
    private Date enrollmentDate;
    @Column(nullable = false)
    private Boolean isComplete;

}
