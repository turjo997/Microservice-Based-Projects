package com.bjit.tss.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WrittenTestModel {
    private Long writtenId;
    private Long hiddenCode;
    private Long applicantId;
    private Double mark;
    private String circular;
}
