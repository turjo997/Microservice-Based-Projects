package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.model.TaskSubmissionModel;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class SubmissionService {

    private final SubmissionRepository submissionRepo;
    private final TaskRepository taskRepo;
    private final TraineeRepository traineeRepo;

    public ResponseEntity<Submission> createSubmission(MultipartFile answer , TaskSubmissionModel taskSubmissionModel, Long taskId , Long traineeId) throws IOException {

        final String UPLOAD_URL = new ClassPathResource("static/submissions/").getFile().getAbsolutePath();
        Files.copy( answer.getInputStream() , Paths.get(UPLOAD_URL + "/" + traineeId.toString() + File.separator + answer.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING); // + model.getName() + File.separator
        String answerLink = ServletUriComponentsBuilder.fromCurrentContextPath().path("/submissions/" + traineeId + File.separator + answer.getOriginalFilename()).toUriString();

        Trainee whichTrainee = traineeRepo.findById(traineeId).orElseThrow();
        Task whichTask = taskRepo.findById(taskId).orElseThrow();

        Submission newSubmission = Submission.builder()
                .comment(taskSubmissionModel.getComment())
                .dateAndTime(taskSubmissionModel.getDateAndTime())
                .trainee(whichTrainee)
                .task(whichTask)
                .answerFile(answerLink)
                .build();
        Submission savedSubmission = submissionRepo.save(newSubmission);

        return new ResponseEntity<>(savedSubmission , HttpStatus.CREATED);
    }

    public ResponseEntity<List<Submission>> viewAllSubmissions() {

        return new ResponseEntity<>( submissionRepo.findAll() , HttpStatus.OK);

    }

    public ResponseEntity<Submission> findSubmissionBySubmissionId(Long submissionId) {

        return new ResponseEntity<>( submissionRepo.findById(submissionId).orElseThrow() , HttpStatus.OK);

    }
}
