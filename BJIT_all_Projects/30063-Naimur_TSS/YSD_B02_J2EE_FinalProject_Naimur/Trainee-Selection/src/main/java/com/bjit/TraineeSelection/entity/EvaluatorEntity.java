package com.bjit.TraineeSelection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluator_reg")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluatorId;

    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String contactNumber;
    private String specialization;
    private Long userId;
}
