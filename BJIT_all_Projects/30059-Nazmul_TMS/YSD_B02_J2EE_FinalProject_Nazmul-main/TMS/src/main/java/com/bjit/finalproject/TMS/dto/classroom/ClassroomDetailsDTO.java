package com.bjit.finalproject.TMS.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomDetailsDTO {
    private String classroomName;
    private Long classroomAttachmentId;
    private String classroomFile;
    private String trainerEmail;
    private String classroomComment;
    private List<ClassroomReplyResponseDTO> classroomReply;
    private String timeStamp;
}
