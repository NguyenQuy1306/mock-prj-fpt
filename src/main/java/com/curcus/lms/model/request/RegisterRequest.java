package com.curcus.lms.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty(message = "Role is mandatory")
    private String userRole;
    @NotEmpty(message = "Name is mandatory")
    private String name;
    private String firstname;
    private String lastname;
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Password is mandatory")
    private String password;
    @NotEmpty(message = "Phone number is mandatory")
    private String phoneNumber;
    private boolean activated = false;
}
