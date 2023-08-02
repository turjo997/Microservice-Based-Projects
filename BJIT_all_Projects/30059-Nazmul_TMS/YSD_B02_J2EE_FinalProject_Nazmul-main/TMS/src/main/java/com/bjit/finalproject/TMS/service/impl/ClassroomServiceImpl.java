package com.bjit.finalproject.TMS.service.impl;

import com.bjit.finalproject.TMS.dto.UserResponseDTO;
import com.bjit.finalproject.TMS.dto.classroom.*;
import com.bjit.finalproject.TMS.dto.scheduleDto.ScheduleBatchResponse;
import com.bjit.finalproject.TMS.exception.ClassroomException;
import com.bjit.finalproject.TMS.exception.IsEmptyException;
import com.bjit.finalproject.TMS.exception.NameNotFoundException;
import com.bjit.finalproject.TMS.model.Batch;
import com.bjit.finalproject.TMS.model.BatchTrainees;
import com.bjit.finalproject.TMS.model.classroom.*;
import com.bjit.finalproject.TMS.repository.*;
import com.bjit.finalproject.TMS.service.ClassroomService;
import com.bjit.finalproject.TMS.service.ScheduleService;
import com.bjit.finalproject.TMS.service.UserService;
import com.bjit.finalproject.TMS.utils.FileDirectoryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classRoomRepo;
    private final ClassroomAttachmentRepository classAttachRepo;
    private final ClassroomReplyRepository classReplyRepo;
    private final ClassroomNoticeRepository classNoticeRepo;
    private final BatchTraineeRepository btRepo;
    private final BatchRepository batchRepo;
    private  final UserService userService;
    private final ScheduleService scheduleService;
    private final FileDirectoryServices fileDirectoryServices;
    @Override
    @Transactional
    public ClassroomPostCreationResponseDTO createClassAttachments(ClassroomRequestDTO requestDTO) {
        String fileName = "";
        if(requestDTO.getFileName()!=null){
            fileName = "Classroom_"+ requestDTO.getFileName().getOriginalFilename();
            String path = fileDirectoryServices.classroomFileToSaveAndGet();
            try {
                Files.write(Paths.get(path +"\\" +fileName), requestDTO.getFileName().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Optional<Classroom> requiredClassroom = classRoomRepo.findById(requestDTO.getClassroomId());
        if(requiredClassroom.isEmpty()){
            throw new ClassroomException("No classroom is in database");
        }
        Classroom classRoom = requiredClassroom.get();
        String trainerEmail = userService.loggedInUserEmail();
        //saving the file to ClassRoomAttachment database table
        ClassRoomAttachment savedClassroomAttachment = ClassRoomAttachment.builder()
                                                                .message(requestDTO.getMessage())
                                                                .fileName(fileName)
                                                                .trainerEmail(trainerEmail)
                                                                .classroom(classRoom)
                                                                .build();
        classAttachRepo.save(savedClassroomAttachment);

        String timeStamp = formatDateToString(savedClassroomAttachment.getTimeStamp());
        return ClassroomPostCreationResponseDTO.builder()
                                    .fileName(savedClassroomAttachment.getFileName())
                                    .message(savedClassroomAttachment.getMessage())
                                    .trainerEmail(savedClassroomAttachment.getTrainerEmail())
                                    .timeStamp(timeStamp)
                                    .build();
    }

    @Override
    public ClassroomReplyResponseDTO createReply(ClassroomReplyDTO replyDTO) {
        Optional<ClassRoomAttachment> comment = classAttachRepo.findById(replyDTO.getAttachmentId());
        if(comment.isEmpty()){
            throw new ClassroomException("No comment to reply");
        }
        String email = userService.loggedInUserEmail();
        UserResponseDTO user = userService.getAllUsers()
                                .stream()
                                .filter(u->u.getEmail().equals(email))
                                .findFirst()
                                .orElseThrow(()->{throw new NameNotFoundException("No user by this email");});
        ClassroomReply saveReply = ClassroomReply.builder()
                                                  .reply(replyDTO.getReply())
                                                  .classRoomAttachment(comment.get())
                                                  .email(email)
                                                  .imageFile(user.getImage())
                                                  .build();
        classReplyRepo.save(saveReply);
        return ClassroomReplyResponseDTO.builder()
                                .userEmail(saveReply.getEmail())
                                .message(saveReply.getClassRoomAttachment().getMessage())
                                .reply(saveReply.getReply())
                                .imageFile(user.getImage())
                                .build();
    }

    @Override
    public ClassroomNoticeDTO createNotice(ClassroomNoticeDTO noticeDTO) {
        Optional<Classroom> classroom = classRoomRepo.findById(noticeDTO.getClassroomId());
        if(classroom.isEmpty()){
            throw new ClassroomException("No classroom to post notice");
        }
        ClassroomNotice notice = ClassroomNotice.builder()
                                                .notice(noticeDTO.getNotice())
                                                .classroom(classroom.get())
                                                .build();
        classNoticeRepo.save(notice);
        return ClassroomNoticeDTO.builder()
                                .notice(notice.getNotice())
                                .build();
    }

    //Getting each attachments of a classroom
    @Override
    public List<ClassroomDetailsDTO> getClassroomDetails(Long classroomId) {
        Classroom classroom = classRoomRepo.findById(classroomId)
                              .orElseThrow(()->{throw new ClassroomException("No classroom");});

//        Classroom  classroom = requiredClass.get();
        List<ClassRoomAttachment> attachments = classAttachRepo.findByClassroom(classroom);
        if(attachments.isEmpty()){
            throw new IsEmptyException("No contents for this classroom");
        }
        List<ClassroomDetailsDTO> responses = new ArrayList<>();
        for(ClassRoomAttachment attachment:attachments){
            List<ClassroomReply> replies = classReplyRepo.findByClassRoomAttachment(attachment);
            List<ClassroomReplyResponseDTO> classroomReplies = replies.stream()
                                                                        .map(r->ClassroomReplyResponseDTO
                                                                                .builder()
                                                                                .message("Replies to this message")
                                                                                .reply(r.getReply())
                                                                                .userEmail(r.getEmail())
                                                                                .imageFile(r.getImageFile())
                                                                                .build())
                                                                        .collect(Collectors.toList());
            ClassroomDetailsDTO response = ClassroomDetailsDTO.builder()
                                                              .classroomName(classroom.getClassroomTitle())
                                                              .classroomAttachmentId(attachment.getAttachmentId())
                                                              .classroomFile(attachment.getFileName())
                                                              .trainerEmail(attachment.getTrainerEmail())
                                                              .classroomComment(attachment.getMessage())
                                                              .classroomReply(classroomReplies)
                                                              .timeStamp(formatDateToString(attachment.getTimeStamp()))
                                                              .build();
            responses.add(response);
        }
        return responses;
    }
    @Override
    public List<ClassroomNoticeDTO> getClassroomNotices(Long classroomId){
        Classroom classroom = classRoomRepo.findById(classroomId)
                              .orElseThrow(()->{throw new ClassroomException("No classroom");});
        List<ClassroomNotice> requiredNotices = classNoticeRepo.findAllByClassroom(classroom);
        List<ClassroomNoticeDTO> notices = requiredNotices.stream()
                                                        .map(n->ClassroomNoticeDTO.builder()
                                                                .classroomId(n.getClassroom().getClassroom_id())
                                                                .notice(n.getNotice())
                                                                .build())
                                                        .collect(Collectors.toList());
        return notices;
    }
    @Override
    public List<ClassroomResponse> getClassroom() {
        String loggedInUser = userService.loggedInUserEmail();

        String role = userService.getUserRole();
        if(role.equals("TRAINER")){
            return getClassroomByTrainer(loggedInUser);
        }
        else if(role.equals("TRAINEE")){
            BatchTrainees batchTrainees = btRepo.findByTraineeEmail(loggedInUser)
                                          .orElseThrow(()->{throw new NameNotFoundException("No trainee by this name in this batch");});
            Batch batch = batchRepo.findByBatchName(batchTrainees.getBatchName())
                                    .orElseThrow(()->{throw new NameNotFoundException("No batch by this name");});
            Classroom classroom = classRoomRepo.findByBatch(batch)
                                               .orElseThrow(()->{throw new ClassroomException("No classroom found");});
            List<ClassroomResponse> classrooms = new ArrayList<>();
            ClassroomResponse classroomResponse = ClassroomResponse.builder()
                                                                    .classroomId(classroom.getClassroom_id())
                                                                    .classroomName(classroom.getClassroomTitle())
                                                                    .build();
            classrooms.add(classroomResponse);
            return classrooms;
        }
        List<Classroom> classrooms = classRoomRepo.findAll();
        List<ClassroomResponse> responses = classrooms.stream()
                                        .map(c->ClassroomResponse
                                                .builder()
                                                .classroomId(c.getClassroom_id())
                                                .classroomName(c.getClassroomTitle())
                                                .build())
                                        .collect(Collectors.toList());
        return responses;
    }
    private List<ClassroomResponse> getClassroomByTrainer(String email){
        List<ScheduleBatchResponse> schedules = scheduleService.getScheduleTrainerHelper(email);
        List<String> batches = new ArrayList<>();
        for(ScheduleBatchResponse response:schedules){
            batches  = response.getBatchName().stream().collect(Collectors.toList());
        }
        List<Classroom> requiredClassroom = new ArrayList<>();
        for(String batchName:batches){
            Batch batch = batchRepo.findByBatchName(batchName).get();
            Classroom classroom = classRoomRepo.findByBatch(batch)
                                            .orElseThrow(()->{throw new ClassroomException("No classroom for this batch");});
            requiredClassroom.add(classroom);
        }
        List<ClassroomResponse> responses= requiredClassroom.stream()
                                            .map(c->ClassroomResponse.builder()
                                                    .classroomId(c.getClassroom_id())
                                                    .classroomName(c.getClassroomTitle())
                                                    .build())
                                            .collect(Collectors.toList());
        return responses;
    }
    private String formatDateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
//    private String pathToSaveAndGet(){
//
//    }
