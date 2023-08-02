package com.bjit.tss.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluatorAssignmentResponse {
    private String candidateName;
    private String candidateEmail;
    private String evaluatorEmail;
    private String evaluatorName;
}
