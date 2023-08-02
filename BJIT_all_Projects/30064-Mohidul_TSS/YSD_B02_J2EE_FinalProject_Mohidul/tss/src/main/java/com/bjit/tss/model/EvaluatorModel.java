package com.bjit.tss.model;

import com.bjit.tss.entity.CircularEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluatorModel {
    private Long evaluatorId;
    private String evaluatorName;
    private String specialization;
    private CircularEntity title;
}
