package com.curcus.lms.model.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.mapping.Set;

import com.curcus.lms.model.entity.UserRole.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
// @AllArgsConstructor
// @NoArgsConstructor
@Entity
@DiscriminatorValue(value = Role.STUDENT)
@Table(name = "students") // Define table name explicitly
@PrimaryKeyJoinColumn(name = "studentId", referencedColumnName = "userId")
public class Student extends User {
    // Add any additional properties or relationships specific to Student here
    @OneToMany(mappedBy = "student")
    @JsonIgnore

    java.util.Set<Enrollment> enrollment;
    @OneToMany(mappedBy = "student")
    @JsonIgnore

    java.util.Set<Cart> cart;
}
