package com.bjit.trainingmanagementsystem.models.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchAssignmentRequest {

    private Long traineeId;
    private Long trainerId;
    private Long batchId;
}
