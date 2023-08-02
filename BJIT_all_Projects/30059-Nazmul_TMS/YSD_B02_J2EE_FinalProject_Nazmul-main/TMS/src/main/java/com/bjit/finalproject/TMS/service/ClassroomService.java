package com.bjit.finalproject.TMS.service;

import com.bjit.finalproject.TMS.dto.classroom.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassroomService {
    ClassroomPostCreationResponseDTO createClassAttachments(ClassroomRequestDTO requestDTO);
    ClassroomReplyResponseDTO createReply(ClassroomReplyDTO replyDTO);
    ClassroomNoticeDTO createNotice(ClassroomNoticeDTO noticeDTO);
    List<ClassroomDetailsDTO> getClassroomDetails(Long classroomId);
    List<ClassroomNoticeDTO> getClassroomNotices(Long classroomId);
    List<ClassroomResponse> getClassroom();
}
