package com.bjit.TraineeSelection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assign_evaluator")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignEvaluator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignId;
    private Long evaluatorId;
    private Long circularId;
}
