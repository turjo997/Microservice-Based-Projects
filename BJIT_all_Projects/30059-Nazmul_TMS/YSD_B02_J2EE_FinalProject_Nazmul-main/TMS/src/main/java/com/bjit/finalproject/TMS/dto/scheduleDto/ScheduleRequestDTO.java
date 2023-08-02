package com.bjit.finalproject.TMS.dto.scheduleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleRequestDTO {
    private String scheduleName;
    private String startTime;
    private String endTime;
    private String courseTitle;
    private List<String> batchName;
    private String trainerEmail;
}
