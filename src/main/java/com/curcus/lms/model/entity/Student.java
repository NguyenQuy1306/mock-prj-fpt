package com.curcus.lms.model.entity;

import jakarta.persistence.*;
import lombok.*;

import com.curcus.lms.model.entity.UserRole.Role;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(value=Role.STUDENT)
public class Student extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Student_id")
    private Long student_id;
}
