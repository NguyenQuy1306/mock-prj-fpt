package com.curcus.lms.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "section_completion")
public class SectionCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sectionCompletionId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    private Course CourseId;
    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "sectionId")
    private Section sectionId;
}
