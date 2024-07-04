package com.curcus.lms.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentRequest {

    private String name;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}