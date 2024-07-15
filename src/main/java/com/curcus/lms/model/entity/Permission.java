package com.curcus.lms.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    STUDENT_CREATE("student:create"),
    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student:update"),
    STUDENT_DELETE("student:delete"),
    INSTRUCTOR_CREATE("instructor:create"),
    INSTRUCTOR_READ("instructor:read"),
    INSTRUCTOR_UPDATE("instructor:update"),
    INSTRUCTOR_DELETE("instructor:delete"),
    COURSE_CREATE("course:create"),
    COURSE_READ("course:read"),
    COURSE_UPDATE("course:update"),
    COURSE_DELETE("course:delete"),
    ENROLLMENT_CREATE("enrollment:create"),
    ENROLLMENT_READ("enrollment:read"),
    ENROLLMENT_UPDATE("enrollment:update"),
    ENROLLMENT_DELETE("enrollment:delete"),
    CART_CREATE("cart:create"),
    CART_READ("cart:read"),
    CART_UPDATE("cart:update"),
    CART_DELETE("cart:delete"),
    CATEGORY_CREATE("category:create"),
    CATEGORY_READ("category:read"),
    CATEGORY_UPDATE("category:update"),
    CATEGORY_DELETE("category:delete"),
    ;

    @Getter
    private final String permission;
}
