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
public class SecurityConfiguration {
	
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
            "/api/password-reset/**",
            "/api/v1/auth/register",
            "/api/v1/auth/authenticate",
            "/api/v1/cookie/**"
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.anyRequest().permitAll())
//                .authorizeHttpRequests(req ->
//                        req.requestMatchers(WHITE_LIST_URL)
//                                .permitAll()
//
//                                .requestMatchers("/api/v1/test/**").hasAnyRole(ADMIN.name(), STUDENT.name())
//                                .requestMatchers(GET, "/api/v1/test/**").hasAuthority(STUDENT_READ.name())
//                                .requestMatchers(POST, "/api/v1/test/**").hasAuthority(STUDENT_CREATE.name())
//                                .requestMatchers(PUT, "/api/v1/test/**").hasAuthority(STUDENT_UPDATE.name())
//                                .requestMatchers(DELETE, "/api/v1/test/**").hasAuthority(STUDENT_DELETE.name())
//
//                                .requestMatchers("/api/students/**").hasAnyRole(ADMIN.name(), STUDENT.name())
//                                .requestMatchers(GET, "/api/students/**").hasAnyAuthority(ADMIN_READ.name(), STUDENT_READ.name())
//                                .requestMatchers(POST, "/api/students/**").hasAnyAuthority(ADMIN_CREATE.name(), STUDENT_CREATE.name())
//                                .requestMatchers(PUT, "/api/students/**").hasAnyAuthority(ADMIN_UPDATE.name(), STUDENT_UPDATE.name())
//                                .requestMatchers(DELETE, "/api/students/**").hasAnyAuthority(ADMIN_DELETE.name(), STUDENT_DELETE.name())
//
//                                .requestMatchers("/api/instructors/**").hasAnyRole(ADMIN.name(), INSTRUCTOR.name())
//                                .requestMatchers(GET, "/api/instructors/**").hasAnyAuthority(ADMIN_READ.name(), INSTRUCTOR_READ.name())
//                                .requestMatchers(POST, "/api/instructors/**").hasAnyAuthority(ADMIN_CREATE.name(), INSTRUCTOR_CREATE.name())
//                                .requestMatchers(PUT, "/api/instructors/**").hasAnyAuthority(ADMIN_UPDATE.name(), INSTRUCTOR_UPDATE.name())
//                                .requestMatchers(DELETE, "/api/instructors/**").hasAnyAuthority(ADMIN_DELETE.name(), INSTRUCTOR_DELETE.name())
//
//                                .requestMatchers("/api/courses/**").hasAnyRole(ADMIN.name(), INSTRUCTOR.name())
//                                .requestMatchers(GET, "/api/courses/**").hasAnyAuthority(COURSE_READ.name())
//                                .requestMatchers(POST, "/api/courses/**").hasAnyAuthority(COURSE_CREATE.name())
//                                .requestMatchers(PUT, "/api/courses/**").hasAnyAuthority(COURSE_UPDATE.name())
//                                .requestMatchers(DELETE, "/api/courses/**").hasAnyAuthority(COURSE_DELETE.name())
//
//                                .anyRequest()
//                                .authenticated()
//                )
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
