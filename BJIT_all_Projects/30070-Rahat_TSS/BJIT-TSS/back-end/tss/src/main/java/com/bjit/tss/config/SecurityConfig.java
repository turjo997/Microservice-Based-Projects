package com.bjit.tss.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Data
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "api/auth/login",
                        "api/auth/register/applicant",
                        "/api/course",
                        "/api/validation",
                        "/api/file-download/image/**"

                )
                .permitAll()
                .requestMatchers(
                        "/api/auth/validation"

                )
                .hasAnyAuthority("USER", "ADMIN", "APPLICANT", "EVALUATOR")
                .requestMatchers(
                        "api/upload/file-upload/image",
                        "api/upload/file-upload/resume",
                        "/api/auth/register/applicant/validation",
                        "api/auth/register/send-email-verification"
                )
                .hasAuthority("USER")
                .requestMatchers(
                        "/api/application/apply",
                        "api/upload/file-upload/image",
                        "api/upload/file-upload/resume",
                        "api/candidate/generate-admit/**",
                        "api/candidate/dashboard",
                        "api/file-download/resume"
                )
                .hasAuthority("APPLICANT")
                .requestMatchers(
                        "/api/course/batch-code/**"
                )
                .hasAnyAuthority("ADMIN", "APPLICANT")
                .requestMatchers(
                        "/api/evaluation/upload-mark/written"
                )
                .hasAuthority("EVALUATOR")
                .requestMatchers(
                        "api/course/create",
                        "/api/course/update/batch-code/**",
                        "/api/application/course/**",
                        "/api/approval/applicant",
                        "/api/email/send",
                        "api/auth/register/evaluator",
                        "api/number-question/set",
                        "api/candidate/all",
                        "/api/evaluation/assign-answer",
                        "/api/data-storage/set",
                        "/api/data-storage/get",
                        "/api/evaluation/upload-mark/aptitude",
                        "/api/evaluation/upload-mark/technical",
                        "/api/evaluation/upload-mark/hr-viva",
                        "/api/final-trainee/all-passed/**",
                        "/api/final-trainee/all-passed/",
                        "/api/course/unavailable",
                        "/api/evaluator/get-all",
                        "/api/evaluator/assigned-candidates/**",
                        "/api/application/course/unassigned-candidates",
                        "/api/evaluation/passed-round",
                        "api/file-download/resume/**",
                        "/api/application/course/distinct",
                        "/api/application/course/distinct_institution"

                )
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}