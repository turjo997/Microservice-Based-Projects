package com.bjit.finalproject.TMS.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v2/login", "api/user/images/{imageName}"
                                    ,"api/assignment/files/{filename:.+}","/api-docs/**")
                .permitAll()
                .requestMatchers("/socket/**").permitAll()
                .requestMatchers("/api/user/registerUser","/api/user/get-all-users",
                                "/api/user/get-all-trainees/{roleName}","/api/user/get-all-trainers/{roleName}",
                                "/api/schedules/create-schedule", "/api/schedules/get-schedules",
                                "/api/schedules/update-schedule/{scheduleId}","/api/schedules/delete-schedule/{scheduleId}",
                                "/api/schedules/get-schedules-by-batch/{batchName}",
                                "/api/course/**",
                                "/api/batch/**"
                                ).hasAuthority("ADMIN")

                .requestMatchers("/api/user/get-current-user","/api/user/get-user-role","/api/classroom/get-assigned-classroom",
                                "/api/classroom/get-classroom-details/{classroomId}",
                                "/api/classroom/get-classroom-notices/{classroomId}",
                                "/api/assignment/get-assignments-by-user",
                                "/api/assignment/get-assignments/{scheduleId}"
                                ).hasAnyAuthority("ADMIN","TRAINER","TRAINEE")

                .requestMatchers("/api/schedules/get-schedules-by-trainer",
                                "/api/classroom/create-attachments","/api/classroom/create-notice",
                                "/api/assignment/create-assignment","/api/assignment/update-assignment/{assignmentId}",
                                "/api/assignment/get-assignment-submissions/{assignmentId}"
                                ).hasAnyAuthority("ADMIN","TRAINER")

                .requestMatchers("/api/schedules/get-schedules-by-trainee")
                                .hasAnyAuthority("ADMIN","TRAINEE")

                .requestMatchers("/api/user/get-user-profile","/api/user/update-profile")
                                .hasAnyAuthority("TRAINEE","TRAINER")

                .requestMatchers("/api/assignment/submit-answer").hasAuthority("TRAINEE")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
