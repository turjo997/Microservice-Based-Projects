package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.model.FinalProjectMarkModel;
import com.example.demo.model.TaskEvaluationModel;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class TaskEvaluationService {

    private final TaskEvaluateRepository taskEvaluateRepo;
    private final TaskRepository taskRepo;
    private final TraineeRepository traineeRepo;

    public ResponseEntity<TaskEvaluation> createTaskEvaluation(TaskEvaluationModel taskEvaluation , Long taskId , Long traineeId){

        Task whichTask = taskRepo.findById(taskId).orElseThrow();
        Trainee whichTrainee = traineeRepo.findById(traineeId).orElseThrow();

        TaskEvaluation newEvaluation = TaskEvaluation.builder()
                .type(whichTask.getTaskType())
                .codingEquipment(taskEvaluation.getCodingEquipment())
                .codeOutput(taskEvaluation.getCodeOutput())
                .codeQuality(taskEvaluation.getCodeQuality())
                .codeDemonstration(taskEvaluation.getCodeDemonstration())
                .codeUnderstanding(taskEvaluation.getCodeUnderstanding())
                .totalMarks(
                                taskEvaluation.getCodingEquipment() +
                                taskEvaluation.getCodeOutput() +
                                taskEvaluation.getCodeQuality() +
                                taskEvaluation.getCodeDemonstration() +
                                taskEvaluation.getCodeUnderstanding()
                            )
                .task(whichTask)
                .trainee(whichTrainee)
                .build();

        TaskEvaluation savedEvaluation = taskEvaluateRepo.save(newEvaluation);

        return new ResponseEntity<>( savedEvaluation , HttpStatus.CREATED);

    }

    public ResponseEntity<List<TaskEvaluation>> viewAllEvaluations() {

        return new ResponseEntity<>( taskEvaluateRepo.findAll() , HttpStatus.OK);
    }

    public ResponseEntity<TaskEvaluation> findTaskEvaluationByEvaluationId(Long evaluationId) {

        return new ResponseEntity<>( taskEvaluateRepo.findById(evaluationId).orElseThrow() , HttpStatus.OK);

    }

}
