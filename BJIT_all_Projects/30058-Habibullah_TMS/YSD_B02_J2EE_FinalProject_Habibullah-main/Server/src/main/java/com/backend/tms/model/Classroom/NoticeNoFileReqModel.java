package com.backend.tms.model.Classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeNoFileReqModel {
    private Long id;
    private String NoticeTitle;
    private Long BatchId;
    private Long trainerId;
}