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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "userId")
    private User instructor;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course", cascade = CascadeType.ALL)
    Set<Enrollment> enrollment;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "course")
    Set<Section> sections;

    @Override
    public String toString() {
        return "Course [courseId=" + courseId + ", title=" + title + ", description=" + description + ", price=" + price
                + ", instructor=" + instructor.getUserId() + ", category=" + category + ", enrollment=" + enrollment
                + "]";
    }

}
