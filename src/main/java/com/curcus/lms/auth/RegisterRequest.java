package com.curcus.lms.auth;

import com.curcus.lms.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String userRole;
    private String name;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
}
