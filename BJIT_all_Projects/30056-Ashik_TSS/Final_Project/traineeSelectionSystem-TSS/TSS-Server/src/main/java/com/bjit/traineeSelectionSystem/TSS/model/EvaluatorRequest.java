package com.bjit.traineeSelectionSystem.TSS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluatorRequest {
    private String email;
    private String password;
    private String name;
    private String title;
}
