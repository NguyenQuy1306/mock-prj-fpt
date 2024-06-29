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
@Table(name = "video_contents")
public class VideoContent {
    @Id
    @GeneratedValue
    private Long videoId;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "courseId")
    private Course courseId;
    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "sectionId")
    private Section sectionId;

    @Column(nullable = false)
    private String videoType;
    @Column(nullable = false)
    private String videoUrl;
}
