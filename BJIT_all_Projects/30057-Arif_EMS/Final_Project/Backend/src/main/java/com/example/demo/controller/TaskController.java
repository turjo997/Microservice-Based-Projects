package com.example.demo.controller;

import com.example.demo.entity.Task;
import com.example.demo.model.TaskCreateModel;
import com.example.demo.service.TaskCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor

public class TaskController {

    private final TaskCreationService taskCreationService;

    @PostMapping("/create/{batchId}/{userId}")
    ResponseEntity<Task> createTask(@RequestParam("question") MultipartFile question , @ModelAttribute TaskCreateModel task , @PathVariable Long batchId , @PathVariable Long userId) throws IOException {
        return taskCreationService.createTask(question , task , batchId , userId);
    }

}
