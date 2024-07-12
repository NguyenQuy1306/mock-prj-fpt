package com.curcus.lms.config;

import com.curcus.lms.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import static com.curcus.lms.model.entity.Permission.*;
import static com.curcus.lms.model.entity.Role.ADMIN;
import static com.curcus.lms.model.entity.Role.STUDENT;
import static com.curcus.lms.model.entity.Role.INSTRUCTOR;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/api/v1/auth/register",
            "/api/v1/auth/authenticate",
            "/api/password-reset/request",
            "/api/password-reset/reset"
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()

                                /// --------------------- STUDENT --------------------------------
                                // /api/students/{id}
                                .requestMatchers(RegexRequestMatcher.regexMatcher(GET, "/api/students/[0-9]+")).hasAnyAuthority(STUDENT_READ.getPermission())
                                .requestMatchers(RegexRequestMatcher.regexMatcher(PUT, "/api/students/[0-9]+")).hasAnyAuthority(STUDENT_UPDATE.getPermission())
                                .requestMatchers(RegexRequestMatcher.regexMatcher(DELETE, "/api/students/[0-9]+")).hasAnyAuthority(STUDENT_DELETE.getPermission())

                                // /api/students/{id}/changePassword
                                .requestMatchers(RegexRequestMatcher.regexMatcher(PUT, "/api/students/[0-9]+/changePassword")).hasAnyAuthority(STUDENT_UPDATE.getPermission())

                                // /api/students
                                .requestMatchers(GET, "/api/students").hasAnyAuthority(STUDENT_READ.getPermission())
                                .requestMatchers(POST, "/api/students").hasAnyAuthority(STUDENT_CREATE.getPermission())

                                // TODO  /api/students/{studentId}/courses/{courseId} và /api/students/{studentId}/enrollFromCart
                                // 2 cái này chưa biết phân sao tại còn dính tới phần payment

                                // /api/students/{id}/courses
                                .requestMatchers(GET, "/api/students/[0-9]+/courses").hasAnyAuthority(COURSE_READ.getPermission())

                                // /api/students/{id}/cart
                                .requestMatchers(GET, "/api/students/[0-9]+/cart").hasAnyAuthority(CART_READ.getPermission())

                                // /api/students/list
                                .requestMatchers(GET, "/api/students/list").hasAnyAuthority(STUDENT_READ.getPermission())

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}
