package com.curcus.lms.model.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;
import java.io.Serializable;

@Setter
@Getter
public class CartItems implements Serializable {

    private int studentId;
    private String name;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

}
