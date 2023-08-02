package com.bjit.tss.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "evaluator_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluatorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluator_id")
    private Long evaluatorId;
    private String name;
    private String email;
}