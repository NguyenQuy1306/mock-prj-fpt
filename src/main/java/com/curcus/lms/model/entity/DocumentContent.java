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
@Table(name = "document_contents")
public class DocumentContent {
    @Id
    @GeneratedValue
    private Long documentId;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "sectionId")
    private Section section;
    @Column(nullable = false)
    private String documentType;
    @Column(nullable = false)
    private String documentUrl;
}
