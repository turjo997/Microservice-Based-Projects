package com.bjit.trainingmanagementsystem.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class EndpointConfig {

    @Value("${endpoints.open}")
    private String[] openEndpoints;

    @Value("${endpoints.admin}")
    private String[] adminEndpoints;

    @Value("${endpoints.trainer}")
    private String[] trainerEndpoints;

    @Value("${endpoints.trainee}")
    private String[] traineeEndpoints;

    @Value("${endpoints.traineeTrainer}")
    private String[] traineeTrainerEndpoints;

    @Value("${endpoints.adminTrainer}")
    private String[] adminTrainerEndpoints;

    @Value("${endpoints.adminTrainee}")
    private String[] adminTraineeEndpoints;

    @Value("${endpoints.allRole}")
    private String[] allRoleEndpoints;
}

