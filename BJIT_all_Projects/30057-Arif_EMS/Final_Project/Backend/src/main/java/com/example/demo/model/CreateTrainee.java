package com.example.demo.model;

import lombok.*;
import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CreateTrainee {

    private String institute;
    private Double cgpa;
    private String email;
    private String password;
    private String fullName;
    private String address;

}
