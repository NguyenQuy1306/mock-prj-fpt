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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue
    private Long categoryId;
    @Column(unique = true, nullable = false)
    private String categoryName;
}
