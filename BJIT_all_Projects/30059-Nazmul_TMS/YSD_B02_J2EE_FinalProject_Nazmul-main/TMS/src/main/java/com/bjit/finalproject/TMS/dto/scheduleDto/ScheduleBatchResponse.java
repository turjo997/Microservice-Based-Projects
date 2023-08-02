package com.bjit.finalproject.TMS.dto.scheduleDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleBatchResponse {
    private Long id;
    private String scheduleName;
    private String startTime;
    private String endTime;
    private String courseTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String trainerEmail;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> batchName;
}
