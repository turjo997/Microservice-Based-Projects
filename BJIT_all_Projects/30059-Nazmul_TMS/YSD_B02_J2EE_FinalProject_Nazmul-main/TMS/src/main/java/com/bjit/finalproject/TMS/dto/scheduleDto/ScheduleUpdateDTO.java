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
public class ScheduleUpdateDTO {
    private String title;
    private String trainerEmail;
    private String courseTitle;
    private List<String> batchName;
}
