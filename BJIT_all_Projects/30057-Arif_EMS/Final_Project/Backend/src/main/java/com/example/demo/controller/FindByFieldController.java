package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/find")
@RequiredArgsConstructor

public class FindByFieldController {

    private final RegistrationLoginService authService;
    private final BatchService batchService;
    private final TaskCreationService taskCreationService;
    private final SubmissionService submissionService;
    private final TaskEvaluationService taskEvaluationService;
    private final FinalEvaluationService finalEvaluationService;

    @GetMapping("/user/{userId}")
    ResponseEntity<User> findUserById(@PathVariable Long userId){
        return authService.findUserById(userId);
    }

    @GetMapping("/admin/{adminId}")
    ResponseEntity<Admin> findAdminById(@PathVariable Long adminId){
        return authService.findAdminById(adminId);
    }

    @GetMapping("/trainer/{trainerId}")
    ResponseEntity<Trainer> findTrainerById(@PathVariable Long trainerId){
        return authService.findTrainerById(trainerId);
    }

    @GetMapping("/trainee/{traineeId}")
    ResponseEntity<Trainee> findTraineeById(@PathVariable Long traineeId){
        return authService.findTraineeById(traineeId);
    }

    @GetMapping("/batch/{batchId}")
    ResponseEntity<Batch> findBatchById(@PathVariable Long batchId){
        return batchService.findBatchByBatchId(batchId);
    }

    @GetMapping("/task/{taskId}")
    ResponseEntity<Task> findTaskById(@PathVariable Long taskId){
        return taskCreationService.findTaskByTaskId(taskId);
    }

    @GetMapping("/submission/{submissionId}")
    ResponseEntity<Submission> findSubmissionBySubmissionId(@PathVariable Long submissionId){
        return submissionService.findSubmissionBySubmissionId(submissionId);
    }

    @GetMapping("/task/evaluation/{evaluationId}")
    ResponseEntity<TaskEvaluation> findTaskEvaluationByEvaluationId(@PathVariable Long evaluationId){
        return taskEvaluationService.findTaskEvaluationByEvaluationId(evaluationId);
    }

    @GetMapping("/final/criteria/{batchId}")
    ResponseEntity<Set<String>> findAllCriteriaForFinalProject(@PathVariable Long batchId){
        return finalEvaluationService.viewAllCriteria(batchId);
    }

    @GetMapping("/manager/evaluation/{managerEvaluationId}")
    ResponseEntity<ManagerEvaluation> findManagerEvaluationByManagerEvaluationId(@PathVariable Long managerEvaluationId){
        return finalEvaluationService.findManagerEvaluationByManagerEvaluationId(managerEvaluationId);
    }

    @GetMapping("/hr/evaluation/{hrEvaluationId}")
    ResponseEntity<HREvaluation> findHrEvaluationByHrEvaluationId(@PathVariable Long hrEvaluationId){
        return finalEvaluationService.findHrEvaluationByHrEvaluationId(hrEvaluationId);
    }

}