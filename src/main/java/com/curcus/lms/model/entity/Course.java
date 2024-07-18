package com.curcus.lms.model.entity;

import jakarta.persistence.*;
import jakarta.websocket.Decoder.Text;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
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
    private String courseThumbnail;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = true, columnDefinition = "double precision default 0.0")
    private Double avgRating;

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
