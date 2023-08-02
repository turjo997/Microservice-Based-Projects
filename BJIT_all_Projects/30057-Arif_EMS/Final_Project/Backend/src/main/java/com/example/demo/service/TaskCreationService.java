package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import lombok.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor

public class TaskCreationService {

    private final TaskRepository taskRepo;
    private final BatchRepository batchRepo;
    private final TrainerRepository trainerRepo;
    private final UserRepository userRepo;

    public ResponseEntity<Task> createTask(MultipartFile question , TaskCreateModel task , Long batchId , Long userId) throws IOException {

        final String UPLOAD_URL = new ClassPathResource("static/tasks/").getFile().getAbsolutePath();
        Files.copy( question.getInputStream() , Paths.get(UPLOAD_URL + File.separator + question.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        String questionLink = ServletUriComponentsBuilder.fromCurrentContextPath().path("/tasks/" + question.getOriginalFilename()).toUriString();

        Task newTask = Task.builder()
                .taskTitle(task.getTaskTitle())
                .taskType(task.getTaskType())
                .taskDate(task.getTaskDate())
                .batch( batchRepo.findById(batchId).orElseThrow() )
                .trainer( trainerRepo.findByUser( userRepo.findById(userId).get() ) )
                .questionFile(questionLink)
                .build();
        Task savedTask = taskRepo.save(newTask);

        return new ResponseEntity<>(savedTask , HttpStatus.CREATED);

    }

    public ResponseEntity<List<Task>> viewAllTasks(){

        return new ResponseEntity<>( taskRepo.findAll() , HttpStatus.OK);

    }

    public ResponseEntity<Task> findTaskByTaskId(Long taskId){

        return new ResponseEntity<>( taskRepo.findById(taskId).orElseThrow() , HttpStatus.OK);

    }

}
