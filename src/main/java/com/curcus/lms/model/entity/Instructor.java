package com.curcus.lms.model.entity;

import com.curcus.lms.model.entity.UserRole.Role;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(value=Role.INSTRUCTOR)
public class Instructor extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Instructor_id")
    private Long instructor_id;
}
