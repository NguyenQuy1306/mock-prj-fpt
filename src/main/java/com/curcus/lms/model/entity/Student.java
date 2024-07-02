package com.curcus.lms.model.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.mapping.Set;

import com.curcus.lms.model.entity.UserRole.Role;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = Role.STUDENT)
@Table(name = "students") // Define table name explicitly
public class Student extends User {

    // Add any additional properties or relationships specific to Student here
    @OneToMany(mappedBy = "student")
    java.util.Set<Enrollment> enrollment;

}
