package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "final_project_criteria")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalProjectCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long criteriaId;
    private Long batchId;
    private String criteria;

}
