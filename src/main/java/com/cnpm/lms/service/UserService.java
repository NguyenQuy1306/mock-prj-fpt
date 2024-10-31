package com.cnpm.lms.service;

import com.cnpm.lms.model.request.RegisterRequest;
import com.cnpm.lms.model.response.UserResponse;

public interface UserService {
    UserResponse createUser(RegisterRequest registerRequest);

    UserResponse updateUser(RegisterRequest registerRequest);

    UserResponse deleteUser(String userId);

    UserResponse getUser(String userId);

    UserResponse getUserByEmail(String email);

    UserResponse getUserByUsername(String username);

    UserResponse activateUser(String email);
}
