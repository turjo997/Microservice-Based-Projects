package com.onlinebookshop.apigateway.securityConfig;

import com.onlinebookshop.apigateway.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final EndpointConfig endpointConfig;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers(endpointConfig.getAuthEndpoints())
                .permitAll()
                .pathMatchers(endpointConfig.getAdminEndpoints())
                .hasAuthority("ADMIN")
                .pathMatchers(endpointConfig.getUserEndpoints())
                .hasAuthority("USER")
                .anyExchange()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.REACTOR_CONTEXT)
                .build();
    }
}