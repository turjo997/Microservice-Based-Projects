package com.bjit.finalproject.TMS.service.impl;

import com.bjit.finalproject.TMS.dto.assignment.*;
import com.bjit.finalproject.TMS.exception.*;
import com.bjit.finalproject.TMS.exception.AssignmentDeadlineException;
import com.bjit.finalproject.TMS.model.*;
import com.bjit.finalproject.TMS.repository.*;
import com.bjit.finalproject.TMS.service.AssignmentService;
import com.bjit.finalproject.TMS.service.UserService;
import com.bjit.finalproject.TMS.utils.DateFormatterService;
import com.bjit.finalproject.TMS.utils.FileDirectoryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final BatchRepository batchRepo;
    private final ScheduleRepository scheduleRepo;
    private final TrainerRepository trainerRepo;
    private final BatchTraineeRepository btRepo;
    private final UserService userService;
    private final AssignmentAnswerRepository assignmentAnswerRepo;
    private final DateFormatterService dateFormatterService;
    private final FileDirectoryServices fileDirectoryServices;
    @Override
    public AssignmentResponseDTO createAssignment(AssignmentRequestDTO assignmentRequestDTO) {
        return assignmentCreateHelper(assignmentRequestDTO);
    }
    @Override
    public List<AssignFinalResponseDTO> getAssignments(){
        String loggedInUser = userService.loggedInUserEmail();
//        if(!email.equals(loggedInUser)){
//            throw new NameNotFoundException("Request is not from the logged-in user");
//        }
        String role = userService.getUserRole();
        if(role.equals("TRAINER")){
            List<AssignFinalResponseDTO> trainerAssignments = getAssignmentsByTrainer(loggedInUser);
            return trainerAssignments;
        }
        else if(role.equals("TRAINEE")){
            List<AssignFinalResponseDTO> traineeAssignments = getAssignmentsByTrainee(loggedInUser);
            return traineeAssignments;
        }

        List<AssignFinalResponseDTO> allAssignments = getAllAssignments();
        return allAssignments;

    }
    @Override
    public List<AssignmentResponseDTO> getAssignmentsBySchedules(Long scheduleId) {
        Schedule requiredSchedule = scheduleRepo.findById(scheduleId).get();
        List<Assignment> allAssignments = assignmentRepository.findBySchedule(requiredSchedule);
        if(allAssignments.isEmpty()){
            throw new ScheduleException("No assignments in this schedule");
        }
        List<AssignmentResponseDTO> assignments = allAssignments.stream()
                                                    .map(a->AssignmentResponseDTO
                                                            .builder()
                                                            .id(a.getAssignmentId())
                                                            .assignmentTitle(a.getAssignmentTitle())
                                                            .fileName(a.getFileName())
                                                            .endTime(dateFormatterService.formatDateForClient(a.getEndTime()))
                                                            .build())
                                                    .collect(Collectors.toList());
        return assignments;
    }
    @Override
    public String updateAssignment(Long id, AssignmentUpdateDTO updateDTO) {
        String path = fileDirectoryServices.assignmentPathToSaveAndGet();
        Assignment assignmentToUpdate = assignmentRepository.findById(id)
                                        .orElseThrow(()->
                                        {throw new NameNotFoundException("no assignment found by this id");});
        assignmentToUpdate.setAssignmentTitle(updateDTO.getAssignmentTitle());
        if(updateDTO.getFile()!=null  && !updateDTO.getFile().isEmpty()){
            String fileName = updateDTO.getFile().getOriginalFilename();
            try {
                Files.write(Paths.get(path +"\\" +fileName), updateDTO.getFile().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            assignmentToUpdate.setFileName(fileName);
        }
        assignmentRepository.saveAndFlush(assignmentToUpdate);
        return "Assignment is updated";
    }

    @Override
    public AssignmentAnswerResponse submitAnswer(AssignmentAnswerRequestDTO answerDTO) {
        return answerCreateHelper(answerDTO);
    }
    @Override
    public List<AssignmentAnswerResponse> getAssignmentSubmissions(Long assignmentId){
        Assignment assignment = assignmentRepository.findById(assignmentId)
                                .orElseThrow(()->{throw new NameNotFoundException("No assignment by this id");});
        List<AssignmentAnswer> requiredAnswers = assignmentAnswerRepo.findByAssignment(assignment);
        List<AssignmentAnswerResponse> answersResponse = requiredAnswers.stream()
                                                                .map(a->AssignmentAnswerResponse.builder()
                                                                        .answerId(a.getAnswerId())
                                                                        .assignmentTitle(a.getAssignment().getAssignmentTitle())
                                                                        .answerFile(a.getAssignmentAnswerFile())
                                                                        .submittedBy(a.getTraineeEmail())
                                                                        .build())
                                                                .collect(Collectors.toList());
        return answersResponse;
    }
    @Transactional
    public AssignmentResponseDTO assignmentCreateHelper(AssignmentRequestDTO assignmentRequestDTO){
        String fileName = "Assignment_" + assignmentRequestDTO.getAssignmentFile().getOriginalFilename();
        String title = assignmentRequestDTO.getAssignmentTitle();
        String path = fileDirectoryServices.assignmentPathToSaveAndGet();

        if(assignmentRepository.findByAssignmentTitle(title).isPresent()){
            throw new AlreadyExistsException("Already a previous assignment exists by this name");
        }
        Schedule schedule =  scheduleRepo.findById(assignmentRequestDTO.getScheduleId()).get();
        try {
            Files.write(Paths.get(path +"\\" +fileName), assignmentRequestDTO.getAssignmentFile().getBytes());
        } catch (IOException e) {
//            e.printStackTrace();
            throw new CreationException("A file needs to be uploaded");
        }
        if(!assignmentRequestDTO.equals( null)){
            Assignment requiredAssignment = Assignment.builder()
                    .assignmentTitle(title)
                    .fileName(fileName)
                    .endTime(dateFormatterService.formatDateForDatabase(assignmentRequestDTO.getEndTime()))
                    .schedule(schedule)
                    .build();
            assignmentRepository.save(requiredAssignment);

            return AssignmentResponseDTO.builder()
                    .id(requiredAssignment.getAssignmentId())
                    .assignmentTitle(requiredAssignment.getAssignmentTitle())
                    .fileName(requiredAssignment.getFileName())
                    .endTime(dateFormatterService.formatDateForClient(requiredAssignment.getEndTime()))
                    .message("ASSIGNMENT CREATED")
                    .build();
        }
        else{
            throw new CreationException("Cannot create the assignment");
        }
    }
    private List<AssignFinalResponseDTO> getAllAssignments(){
        List<Schedule> allBatchSchedules = scheduleRepo.findAll();
        List<AssignFinalResponseDTO>requiredAssignments = allBatchSchedules.stream().map(s->{
                                List<Assignment> allAssignments = assignmentRepository.findBySchedule(s);
                                List<AssignmentResponseDTO> assignments = allAssignments.stream()
                                                                          .map(a->AssignmentResponseDTO
                                                                          .builder()
                                                                          .assignmentTitle(a.getAssignmentTitle())
                                                                          .endTime(a.getEndTime())
                                                                          .fileName(a.getFileName())
                                                                          .id(a.getAssignmentId())
                                                                          .build())
                                                                          .collect(Collectors.toList());
                                return new AssignFinalResponseDTO(s.getScheduleName(),s.getScheduleId(),assignments);
                                }).collect(Collectors.toList());

        return requiredAssignments;
    }
    private List<AssignFinalResponseDTO> getAssignmentsByTrainer(String email){
        TrainerModel trainer = trainerRepo.findByEmail(email)
                                .orElseThrow(()->{throw new NameNotFoundException("No trainer by this email");});
        List<Schedule> trainerSchedules = scheduleRepo.findAllScheduleByTrainer(trainer);
        List<AssignFinalResponseDTO>requiredAssignments = trainerSchedules.stream().map(s->{
            List<Assignment> allAssignments = assignmentRepository.findBySchedule(s);
            List<AssignmentResponseDTO> assignments = allAssignments.stream()
                    .map(a->AssignmentResponseDTO
                            .builder()
                            .assignmentTitle(a.getAssignmentTitle())
                            .endTime(a.getEndTime())
                            .fileName(a.getFileName())
                            .id(a.getAssignmentId())
                            .build())
                    .collect(Collectors.toList());
            return new AssignFinalResponseDTO(s.getScheduleName(),s.getScheduleId(),assignments);
        }).collect(Collectors.toList());
        return requiredAssignments;
    }
    private List<AssignFinalResponseDTO> getAssignmentsByTrainee(String email){
        BatchTrainees traineeBatch = btRepo.findByTraineeEmail(email)
                                    .orElseThrow(()->{throw new NameNotFoundException("No trainee by this name in this batch");});
        Batch batch = batchRepo.findByBatchName(traineeBatch.getBatchName())
                                    .orElseThrow(()->{throw new NameNotFoundException("No batch by this name");});
        List<Schedule> traineeSchedules = scheduleRepo.findAllScheduleByBatch(batch);
        if(traineeSchedules.isEmpty()){
            throw new IsEmptyException("No Schedules for trainer at this moment");
        }
        List<AssignFinalResponseDTO>requiredAssignments = traineeSchedules.stream().map(s->{
            List<Assignment> allAssignments = assignmentRepository.findBySchedule(s);
            List<AssignmentResponseDTO> assignments = allAssignments.stream()
                    .map(a->AssignmentResponseDTO
                            .builder()
                            .assignmentTitle(a.getAssignmentTitle())
                            .endTime(a.getEndTime())
                            .fileName(a.getFileName())
                            .id(a.getAssignmentId())
                            .build())
                    .collect(Collectors.toList());
            return new AssignFinalResponseDTO(s.getScheduleName(),s.getScheduleId(),assignments);
        }).collect(Collectors.toList());
        return requiredAssignments;
    }
    private AssignmentAnswerResponse answerCreateHelper(AssignmentAnswerRequestDTO answerDTO){
        String email = userService.loggedInUserEmail();
        String path = fileDirectoryServices.submissionPathToSaveAndGet();
//        String answerPath = pathToSaveAnswerFiles();
        Optional<Assignment> assignmentOpt = assignmentRepository.findById(answerDTO.getAssignmentId());
        if(!assignmentOpt.isPresent()){
            throw new NameNotFoundException("No assignment in this ID");
        }
        Assignment assignment = assignmentOpt.get();
        boolean isPassed = checkDeadline(assignment);
        if(isPassed){
            throw new AssignmentDeadlineException("DEADLINE PASSED");
        }

        if(answerDTO.getAnswerFile().isEmpty()){
            throw new IsEmptyException("Cannot submit empty file");
        }

        String fileName = "Submission_"+email + "_"+ answerDTO.getAnswerFile().getOriginalFilename();
        try {
            Files.write(Paths.get(path +"\\" +fileName), answerDTO.getAnswerFile().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AssignmentAnswer submittedAnswer = AssignmentAnswer.builder()
                                            .assignment(assignment)
                                            .assignmentAnswerFile(fileName)
                                            .traineeEmail(email)
                                            .build();
        assignmentAnswerRepo.save(submittedAnswer);
        return AssignmentAnswerResponse.builder()
                                        .answerId(submittedAnswer.getAnswerId())
                                        .answerFile(submittedAnswer.getAssignmentAnswerFile())
                                        .assignmentTitle(submittedAnswer.getAssignment().getAssignmentTitle())
                                        .submittedBy(submittedAnswer.getTraineeEmail())
                                        .build();
    }

    private boolean checkDeadline(Assignment assignment){
        String assignmentDeadline = assignment.getEndTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean isPassed = false;
        try{
            Date assignmentDeadlineDate = formatter.parse(assignmentDeadline);
            Date now = new Date();
            isPassed = now.after(assignmentDeadlineDate);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return isPassed;
    }
}
