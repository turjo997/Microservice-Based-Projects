package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "hrEvaluation" )

public class HREvaluation {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long hrEvaluationId;

    private Double aptitudeEvaluation;
    private Double hrEvaluation;
    private Double totalMarks;

    @OneToOne
    @JoinColumn(name = "traineeId")
    private Trainee trainee;

}
