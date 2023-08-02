package com.bjit.finalproject.TMS.dto.courseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCreateDTO {
    private String courseName;
    private String courseDescription;
}
