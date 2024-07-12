package com.curcus.lms.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.curcus.lms.model.entity.Permission.*;

@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(Set.of(
            ADMIN_CREATE,
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            STUDENT_CREATE,
            STUDENT_READ,
            STUDENT_UPDATE,
            STUDENT_DELETE,
            INSTRUCTOR_CREATE,
            INSTRUCTOR_READ,
            INSTRUCTOR_UPDATE,
            INSTRUCTOR_DELETE,
            COURSE_CREATE,
            COURSE_READ,
            COURSE_UPDATE,
            COURSE_DELETE,
            ENROLLMENT_CREATE,
            ENROLLMENT_READ,
            ENROLLMENT_UPDATE,
            ENROLLMENT_DELETE,
            CART_CREATE,
            CART_READ,
            CART_UPDATE,
            CART_DELETE
    )),
    STUDENT(Set.of(
            STUDENT_READ,
            STUDENT_UPDATE,
            COURSE_READ,
            ENROLLMENT_CREATE,
            ENROLLMENT_READ,
            CART_READ,
            CART_UPDATE,
            CART_DELETE
    )),
    INSTRUCTOR(Set.of(
            INSTRUCTOR_READ,
            INSTRUCTOR_UPDATE,
            COURSE_CREATE,
            COURSE_READ,
            COURSE_UPDATE,
            COURSE_DELETE,
            ENROLLMENT_READ
    ))

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}