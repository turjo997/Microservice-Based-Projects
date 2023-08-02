package com.onlinebookshop.apigateway.securityConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointConfig {

    @Value("${endpoints.auth}")
    private String[] authEndpoints;

    @Value("${endpoints.admin}")
    private String[] adminEndpoints;

    @Value("${endpoints.user}")
    private String[] userEndpoints;

    public String[] getAuthEndpoints() {
        return authEndpoints;
    }

    public String[] getAdminEndpoints() {
        return adminEndpoints;
    }

    public String[] getUserEndpoints() {
        return userEndpoints;
    }
}

