package com.curcus.lms.service.impl;

import com.curcus.lms.exception.IncorrectPasswordException;
import com.curcus.lms.exception.UserNotFoundException;
import com.curcus.lms.model.entity.*;
import com.curcus.lms.model.request.AuthenticationRequest;
import com.curcus.lms.model.request.RegisterRequest;
import com.curcus.lms.model.response.AuthenticationResponse;
import com.curcus.lms.repository.RefreshTokenRepository;
import com.curcus.lms.repository.TokenRepository;
import com.curcus.lms.repository.UserRepository;
import com.curcus.lms.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public Boolean register(RegisterRequest request) {
        try {
            User user = switch (request.getUserRole().toUpperCase()) {
                case "I" -> new Instructor();
                case "S" -> new Student();
                default -> throw new IllegalArgumentException("Invalid user role: " + request.getUserRole());
            };
            user.setName(request.getName());
            user.setFirstName(request.getFirstname());
            user.setLastName(request.getLastname());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            repository.save(user);

//            var userDetails = UserDetailsImpl.builder()
//                    .user(user)
//                    .role(user.getDiscriminatorValue().equals(UserRole.Role.STUDENT) ? Role.STUDENT : Role.INSTRUCTOR)
//                    .build();
//            var jwtToken = jwtService.generateToken(userDetails);
//            var refreshToken = jwtService.generateRefreshToken(userDetails);
//            saveUserToken(savedUser, jwtToken);
//            return AuthenticationResponse.builder()
//                    .accessToken(jwtToken)
//                    .refreshToken(refreshToken)
//                    .build();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void saveUserRefreshToken(User user, String refreshToken) {
        var token = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        refreshTokenRepository.save(token);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Account does not exist"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new IncorrectPasswordException("Incorrect password");
        }

        var userDetails = UserDetailsImpl.builder()
                .user(user)
                .role(user.getDiscriminatorValue().equals(UserRole.Role.STUDENT) ? Role.STUDENT : Role.INSTRUCTOR)
                .build();
        var jwtToken = jwtServiceImpl.generateToken(userDetails);
        var refreshToken = jwtServiceImpl.generateRefreshToken(userDetails);
        revokeAllUserTokens(user);
        revokeAllUserRefreshTokens(user);
        saveUserToken(user, jwtToken);
        saveUserRefreshToken(user, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void revokeAllUserRefreshTokens(User user) {
        var validUserTokens = refreshTokenRepository.findAllValidRefreshTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        refreshTokenRepository.saveAll(validUserTokens);
    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtServiceImpl.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            var userDetails = UserDetailsImpl.builder()
                    .user(user)
                    .role(user.getDiscriminatorValue().equals(UserRole.Role.STUDENT) ? Role.STUDENT : Role.INSTRUCTOR)
                    .build();
            if (jwtServiceImpl.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtServiceImpl.generateToken(userDetails);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}

