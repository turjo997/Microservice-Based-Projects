package com.bjit.finalproject.TMS.dto.scheduleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResponseDTO {
    private Long id;
    private String scheduleName;
    private String startTime;
    private String endTime;
    private String courseTitle;
    private List<String> batchName;
    private String trainerEmail;
}
