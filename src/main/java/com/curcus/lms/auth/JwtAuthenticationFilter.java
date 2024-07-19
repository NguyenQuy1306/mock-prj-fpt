package com.curcus.lms.auth;

import com.curcus.lms.model.entity.Token;
import com.curcus.lms.model.entity.TokenType;
import com.curcus.lms.model.entity.User;
import com.curcus.lms.model.entity.UserDetailsImpl;
import com.curcus.lms.repository.RefreshTokenRepository;
import com.curcus.lms.repository.TokenRepository;
import com.curcus.lms.service.impl.CookieServiceImpl;
import com.curcus.lms.service.impl.JWTServiceImpl;
import com.curcus.lms.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTServiceImpl jwtServiceImpl;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final TokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CookieServiceImpl cookieServiceImpl;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // final String authHeader = request.getHeader("Authorization");
        String accessToken = cookieServiceImpl
                .getCookie(request, "accessToken")
                .orElse("null");
        String refreshToken = cookieServiceImpl
                .getCookie(request, "refreshToken")
                .orElse("null");
        String userEmail = "";
        // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        // filterChain.doFilter(request, response);
        // return;
        // }
        if (accessToken.equals("null") && refreshToken.equals("null")) {
            filterChain.doFilter(request, response);
            return;
        }
        // jwt = authHeader.substring(7);
        userEmail = jwtServiceImpl.extractUsername(accessToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetailsImpl userDetails = (UserDetailsImpl) this.userDetailsServiceImpl.loadUserByUsername(userEmail);
            var isAccessTokenValid = tokenRepository.findByToken(accessToken)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            var isRefreshTokenValid = refreshTokenRepository.findByToken(refreshToken)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            if (jwtServiceImpl.isTokenValid(accessToken, userDetails) && isAccessTokenValid) {

                setWebAuthentication(userDetails, request);

            } else if (jwtServiceImpl.isTokenValid(refreshToken, userDetails)
                    && isRefreshTokenValid
                    && !(jwtServiceImpl.isTokenValid(accessToken, userDetails) && isAccessTokenValid)) {

                User user = userDetails.getUser();
                revokeAllUserTokens(user);

                accessToken = jwtServiceImpl.generateToken(userDetails);
                saveUserToken(user, accessToken);
                cookieServiceImpl.addCookie(response, "accessToken", accessToken);

                setWebAuthentication(userDetails, request);
            } else {

                filterChain.doFilter(request, response);

            }
        }
        filterChain.doFilter(request, response);
    }

    private void setWebAuthentication(UserDetailsImpl userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
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
}
