package com.bjit.finalproject.TMS.dto.batchDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchTraineeDTO {
    private String traineeEmail;
    private String batchName;
}
