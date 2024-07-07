package com.curcus.lms.model.entity;

import java.time.LocalDateTime;

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
@Table(name = "contents")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ContentId;

    @Column(nullable = false)
    private LocalDateTime dateTime;
    private String docsType;
    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "sectionId")
    private Section section;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id", referencedColumnName = "videoId")
    private VideoContent videoContent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "documentId")
    private DocumentContent documentContent;
}
