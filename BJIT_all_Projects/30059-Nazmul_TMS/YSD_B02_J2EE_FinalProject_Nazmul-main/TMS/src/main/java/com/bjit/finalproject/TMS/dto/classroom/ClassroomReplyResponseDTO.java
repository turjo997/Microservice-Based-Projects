package com.bjit.finalproject.TMS.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomReplyResponseDTO {
    private String message;
    private String reply;
    private String userEmail;
    private String imageFile;
}
