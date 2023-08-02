package com.bjit.finalproject.TMS.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomNoticeDTO {
    private String notice;
    private Long classroomId;
}
