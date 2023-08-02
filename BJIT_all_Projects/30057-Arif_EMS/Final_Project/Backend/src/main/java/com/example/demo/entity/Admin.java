package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "admin" )

public class Admin {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long adminId;

    private String designation;
    private Long yearOfExperience;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

}
