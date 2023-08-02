package com.backend.tms.model.Classroom;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeResModel {
    private Long id;
    private String title;
    private Long classroomId;
    private String senderEmail;
    private Date createdTime;
    private String file;
    private String senderName;
}