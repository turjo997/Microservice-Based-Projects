package com.bjit.TraineeSelection.config;

import com.bjit.TraineeSelection.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()

                .requestMatchers("/applicant/register").permitAll()
                .requestMatchers("/user/login").permitAll()
                .requestMatchers("/admin/application/approve/**").hasAuthority("ADMIN")
                .requestMatchers("/admin/circular/create").hasAuthority("ADMIN")
                .requestMatchers("/admin/circular/**").hasAuthority("ADMIN")
                .requestMatchers("/admin/circular/update").hasAuthority("ADMIN")
                .requestMatchers("/admin/application/**").hasAuthority("ADMIN")
                .requestMatchers("/admin/approvedApplications").hasAuthority("ADMIN")
                .requestMatchers("/admin/evaluator/register").hasAuthority("ADMIN")
                .requestMatchers("/admin/showAllEvaluator").hasAuthority("ADMIN")
                .requestMatchers("/admin/showEvaluatorById/**").hasAuthority("ADMIN")
                .requestMatchers("/admin/evaluator/register").hasAuthority("ADMIN")
                .requestMatchers("/admin/marks/update/**").hasAuthority("ADMIN")
                .requestMatchers("/admin/assign/evaluator").hasAuthority("ADMIN")
                .requestMatchers("admin/finalCandidates").hasAuthority("ADMIN")
                .requestMatchers("/applicant/applications/**").hasAuthority("USER")
                .requestMatchers("/evaluator/writtenMark/**").hasAuthority("EVALUATOR")
                .requestMatchers("/evaluator/marks/**").hasAuthority("EVALUATOR")
                .requestMatchers("/applicant/circular/all").hasAuthority("USER")
                .requestMatchers("/applicant/admit-card/**").hasAuthority("USER")
                .requestMatchers("/applicant/apply").hasAuthority("USER")
                .requestMatchers("/applicant/upload/**").hasAuthority("USER")
//                .requestMatchers("/admin/applicant/showAll").permitAll()
//                .requestMatchers("/admin/marks/upload").permitAll()
//                .requestMatchers("/admin/marks/update/**").permitAll()
//                .requestMatchers("/admin/application/showAll").permitAll()
//                .requestMatchers("/applicant/upload/**").permitAll()
//                .requestMatchers("/evaluator/**").permitAll()
//                .requestMatchers("/evaluator/assignedCircular/**").permitAll()
//                .requestMatchers("/admin/marks/**").permitAll()
//                .requestMatchers("/applicant/view/**").permitAll()
//                .requestMatchers("/applicant/**").permitAll()
//                .requestMatchers("/books/delete/**").permitAll()
//                .requestMatchers("/books/showAll").permitAll()




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


