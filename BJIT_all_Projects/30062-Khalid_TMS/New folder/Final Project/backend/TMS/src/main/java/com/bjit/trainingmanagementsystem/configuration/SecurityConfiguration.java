package com.bjit.trainingmanagementsystem.configuration;

import com.bjit.trainingmanagementsystem.repository.role.UserRepository;
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
public class SecurityConfiguration {

    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final EndpointConfig endpointConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(endpointConfig.getOpenEndpoints())
                .permitAll()
                .requestMatchers(endpointConfig.getAdminEndpoints())
                .hasAuthority("ADMIN")
                .requestMatchers(endpointConfig.getTrainerEndpoints())
                .hasAuthority("TRAINER")
                .requestMatchers(endpointConfig.getTraineeEndpoints())
                .hasAnyAuthority("TRAINEE")
                .requestMatchers(endpointConfig.getTraineeTrainerEndpoints())
                .hasAnyAuthority("TRAINEE","TRAINER")
                .requestMatchers(endpointConfig.getAdminTrainerEndpoints())
                .hasAnyAuthority("ADMIN","TRAINER")
                .requestMatchers(endpointConfig.getAdminTraineeEndpoints())
                .hasAnyAuthority("ADMIN","TRAINEE")
                .requestMatchers(endpointConfig.getAllRoleEndpoints())
                .hasAnyAuthority("TRAINEE","TRAINER","ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
