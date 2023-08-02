package com.bjit.finalproject.TMS.dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignFinalResponseDTO {
    private String scheduleName;
    private Long scheduleId;
    private List<AssignmentResponseDTO> assignments;
}
