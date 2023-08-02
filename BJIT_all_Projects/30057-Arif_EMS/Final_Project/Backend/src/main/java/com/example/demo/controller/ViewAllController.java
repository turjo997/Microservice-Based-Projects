package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/view/all")
@RequiredArgsConstructor

public class ViewAllController {

    private final RegistrationLoginService authService;
    private final BatchService batchService;
    private final TaskCreationService taskCreationService;
    private final SubmissionService submissionService;
    private final TaskEvaluationService taskEvaluationService;
    private final  FinalEvaluationService finalEvaluationService;

    @GetMapping("/users")
    ResponseEntity<List<User>> viewUsers(){
        return authService.viewAllUser();
    }

    @GetMapping("/admins")
    ResponseEntity<List<Admin>> viewAdmins(){
        return authService.viewAllAdmin();
    }

    @GetMapping("/trainers")
    ResponseEntity<List<Trainer>> viewTrainers(){
        return authService.viewAllTrainer();
    }

    @GetMapping("/trainees")
    ResponseEntity<List<Trainee>> viewTrainees(){
        return authService.viewAllTrainee();
    }

    @GetMapping("/batches/{userId}")
    ResponseEntity<List<Batch>> viewBatches(@PathVariable Long userId){
        return batchService.viewAllBatches(userId);
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> viewTasks(){
        return taskCreationService.viewAllTasks();
    }

    @GetMapping("/submissions")
    ResponseEntity<List<Submission>> viewSubmissions(){
        return submissionService.viewAllSubmissions();
    }

    @GetMapping("/task/evaluations")
    ResponseEntity<List<TaskEvaluation>> viewEvaluations(){
        return taskEvaluationService.viewAllEvaluations();
    }

    @GetMapping("/manager/evaluations")
    ResponseEntity<List<ManagerEvaluation>> viewManagerEvaluations(){
        return finalEvaluationService.viewAllManagerEvaluations();
    }

    @GetMapping("/hr/evaluations")
    ResponseEntity<List<HREvaluation>> viewHrEvaluations(){
        return finalEvaluationService.viewAllHrEvaluations();
    }

}
