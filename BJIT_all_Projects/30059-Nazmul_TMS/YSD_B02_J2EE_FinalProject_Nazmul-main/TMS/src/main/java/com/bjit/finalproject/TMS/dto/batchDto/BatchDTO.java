package com.bjit.finalproject.TMS.dto.batchDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchDTO {
    private String batchName;
    private Date startTime;
    private Date endTime;
}
