package com.curcus.lms.model.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorResponse implements Serializable {
    private int instructorId;
    private String name;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
