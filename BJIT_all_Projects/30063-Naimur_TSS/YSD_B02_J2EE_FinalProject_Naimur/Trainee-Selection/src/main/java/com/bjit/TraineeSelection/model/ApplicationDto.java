package com.bjit.TraineeSelection.model;

import com.bjit.TraineeSelection.entity.ApplicantEntity;
import com.bjit.TraineeSelection.entity.Circular;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDto {

    private Long applicationId;
    private boolean approvalStatus;
    private Long circularId;
    private Long applicantId;
}
