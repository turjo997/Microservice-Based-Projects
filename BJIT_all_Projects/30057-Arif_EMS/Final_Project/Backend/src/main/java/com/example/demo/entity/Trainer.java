package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trainer")

public class Trainer {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long trainerId;

    private String designation;
    private String fieldOfExpertise;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private Set<Long> batchIds = new HashSet<>();
}
