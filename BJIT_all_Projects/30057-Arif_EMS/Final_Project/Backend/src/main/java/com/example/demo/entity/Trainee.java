package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trainee")

public class Trainee {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long traineeId;

    private String institute;
    private Double cgpa;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private Long batchId;

}
