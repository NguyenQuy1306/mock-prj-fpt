package com.curcus.lms.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class StudentRequest {

    @NotBlank(message = "Vui lòng điền đầy đủ thông tin")
    private String name;

    @NotBlank(message = "Vui lòng điền đầy đủ thông tin")
    @Email(message = "Vui lòng điền đầy đủ thông tin")
    private String email;

    @NotBlank(message = "Vui lòng điền đầy đủ thông tin")
    private String password;

    private String firstName;
    private String lastName;

    @NotBlank(message = "Vui lòng điền đầy đủ thông tin")
    private String phoneNumber;
}