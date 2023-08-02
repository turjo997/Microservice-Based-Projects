package com.bjit.finalproject.TMS.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomPostCreationResponseDTO {
    private String fileName;
    private String message;
    private String trainerEmail;
    private String timeStamp;
}
