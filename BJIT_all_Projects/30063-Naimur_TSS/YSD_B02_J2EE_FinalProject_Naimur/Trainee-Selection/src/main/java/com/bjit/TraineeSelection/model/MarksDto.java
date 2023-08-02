package com.bjit.TraineeSelection.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarksDto {
    private Long markId;

    private Long applicantId;
    private Long circularId;
    private Long paperCode;
    private Long writtenMarks;
    private Long technicalMarks;
    private Long hrMarks;
}
