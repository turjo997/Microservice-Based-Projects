package com.example.demo.model;

import lombok.*;
import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CreateTrainer {

    private String designation;
    private String fieldOfExpertise;
    private String email;
    private String password;
    private String fullName;
    private String address;

}
