package com.bjit.finalproject.TMS.dto.batchDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchTraineeDetailsDTO {
    private String traineeEmail;
    private String profilePicture;
    private String batchName;
    private String phoneNo;
}
