package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "batch" )

public class Batch {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long batchId;

    private String batchName;
    private Date startDate;

    @OneToMany
    private Set<Trainee> trainees = new HashSet<>();

    @ManyToMany
    private Set<Trainer> trainers = new HashSet<>();

}
