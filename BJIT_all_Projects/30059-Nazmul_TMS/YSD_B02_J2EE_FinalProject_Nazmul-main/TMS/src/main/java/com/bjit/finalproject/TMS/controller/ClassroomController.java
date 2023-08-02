package com.bjit.finalproject.TMS.controller;

import com.bjit.finalproject.TMS.dto.classroom.ClassroomNoticeDTO;
import com.bjit.finalproject.TMS.dto.classroom.ClassroomReplyDTO;
import com.bjit.finalproject.TMS.dto.classroom.ClassroomRequestDTO;
import com.bjit.finalproject.TMS.service.ClassroomService;
import com.bjit.finalproject.TMS.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/classroom")
public class ClassroomController {
    private final ClassroomService classroomService;
    @PostMapping("/create-attachments")
    public ResponseEntity<Object> createClassAttachments(@ModelAttribute ClassroomRequestDTO classRoomDTO){
        return new ResponseEntity<>(classroomService.createClassAttachments(classRoomDTO), HttpStatus.OK);
    }
    @PostMapping("/create-reply")
    public ResponseEntity<Object> createReply(@RequestBody ClassroomReplyDTO replyDTO){
        return new ResponseEntity<>(classroomService.createReply(replyDTO), HttpStatus.OK);
    }
    @PostMapping("/create-notice")
    public ResponseEntity<Object> createNotice(@RequestBody ClassroomNoticeDTO noticeDTO){
        return new ResponseEntity<>(classroomService.createNotice(noticeDTO), HttpStatus.OK);
    }
    @GetMapping("/get-classroom-details/{classroomId}")
    public ResponseEntity<Object> getClassroomDetails(@PathVariable Long classroomId){
        return new ResponseEntity<>(classroomService.getClassroomDetails(classroomId), HttpStatus.OK);
    }
    @GetMapping("/get-classroom-notices/{classroomId}")
    public ResponseEntity<Object> getClassroomNotices(@PathVariable Long classroomId){
        return new ResponseEntity<>(classroomService.getClassroomNotices(classroomId), HttpStatus.OK);
    }
    @GetMapping("/get-assigned-classroom")
    public ResponseEntity<Object> getClassroom(){
        return new ResponseEntity<>(classroomService.getClassroom(), HttpStatus.OK);
    }
}
