package com.curcus.lms.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "Instructor_id")
    private Long instructor_id;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "instructor_id")
    private User user;
}
