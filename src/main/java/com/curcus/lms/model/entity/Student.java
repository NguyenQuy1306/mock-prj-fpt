package com.curcus.lms.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Student_id")
    private Long student_id;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private User user;
}
