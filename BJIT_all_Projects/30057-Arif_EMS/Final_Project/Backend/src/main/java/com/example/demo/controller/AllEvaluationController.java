package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.model.*;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluation")
@RequiredArgsConstructor

public class AllEvaluationController {

    private final TaskEvaluationService taskEvaluationService;
    private final FinalEvaluationService finalEvaluationService;
    private final TotalMarksGenerationService totalMarksGenerationService;

    @PostMapping("/task/create/{taskId}/{traineeId}")
    public ResponseEntity<TaskEvaluation> createTaskEvaluation(@RequestBody TaskEvaluationModel taskEvaluation , @PathVariable Long taskId , @PathVariable Long traineeId){
        return taskEvaluationService.createTaskEvaluation(taskEvaluation , taskId , traineeId);
    }

    @PostMapping("/final/project/criteria/create/{batchId}")
    public ResponseEntity<List<FinalProjectCriteria>> finalProjectCriteria(@PathVariable Long batchId , @RequestBody List<String> criteria){
        return finalEvaluationService.addCriteria(batchId , criteria);
    }

    @PostMapping("/final/project/create/{traineeId}")
    public ResponseEntity<List<FinalProjectMarkModel>> createFinalProjectEvaluation(@PathVariable Long traineeId , @RequestBody List<FinalProjectMarkModel> criteriaMarks){
        return finalEvaluationService.addFinalMarks(traineeId , criteriaMarks);
    }

    @PostMapping("/manager/create/{traineeId}")
    public ResponseEntity<ManagerEvaluation> createManagerEvaluation(@RequestBody ManagerEvaluationModel managerEvaluation , @PathVariable Long traineeId){
        return finalEvaluationService.createManagerEvaluation(managerEvaluation , traineeId);
    }

    @PostMapping("/hr/create/{traineeId}")
    public ResponseEntity<HREvaluation> createHrEvaluation(@RequestBody HREvaluationModel hrEvaluation , @PathVariable Long traineeId){
        return finalEvaluationService.createHrEvaluation(hrEvaluation , traineeId);
    }

    @PostMapping("/final/total/marks/create/{batchId}")
    public ResponseEntity<List<TotalMarks>> createFinalMarksForWholeBatch(@PathVariable Long batchId , @RequestBody FinalMarksWeight finalMarksWeight){
        return totalMarksGenerationService.createFinalMarksForWholeBatch(batchId , finalMarksWeight);
    }

}
