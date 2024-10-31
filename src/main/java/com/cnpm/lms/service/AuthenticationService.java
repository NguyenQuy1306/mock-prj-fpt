package com.cnpm.lms.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.cnpm.lms.model.request.AuthenticationRequest;
import com.cnpm.lms.model.request.RegisterRequest;
import com.cnpm.lms.model.response.AuthenticationResponse;
import com.cnpm.lms.model.response.LoginResponse;
import com.cnpm.lms.model.response.UserResponse;

public interface AuthenticationService {

    UserResponse register(RegisterRequest request);

    LoginResponse authenticate(AuthenticationRequest request, HttpServletResponse response);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException;
}
