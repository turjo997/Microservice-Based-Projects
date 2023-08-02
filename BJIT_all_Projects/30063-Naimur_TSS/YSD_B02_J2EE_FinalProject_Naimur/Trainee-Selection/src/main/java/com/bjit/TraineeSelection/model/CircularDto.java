package com.bjit.TraineeSelection.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CircularDto {
    private Long circularId;
    private String circularName;
    private String detail;
    private LocalDate endDate;
    private String status;
}
