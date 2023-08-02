package com.bjit.trainingmanagementsystem.models.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchUpdateModel {
    private Long batchId;
    private String batchName;
    private String startDate;
    private String endDate;
}
