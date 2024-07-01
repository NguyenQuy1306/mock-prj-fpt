package com.curcus.lms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue
    private Long courseId;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Boolean isFree;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "userId")
    private User instructor;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;
    @OneToMany(mappedBy = "course")
    Set<Enrollment> enrollment;
}
