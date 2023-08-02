package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.model.FinalMarksWeight;
import com.example.demo.repository.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor

public class TotalMarksGenerationService {

    private final BatchRepository batchRepo;
    private final TaskEvaluateRepository taskEvaluateRepo;
    private final ManagerEvaluationRepository managerEvaluationRepo;
    private final HREvaluationRepository hrEvaluationRepo;
    private final TotalMarksRepository finalMarksRepo;

    public ResponseEntity<List<TotalMarks>> createFinalMarksForWholeBatch(Long batchId , FinalMarksWeight weights){

        Batch whichBatch = batchRepo.findById(batchId).orElseThrow();
        Set<Trainee> trainees = whichBatch.getTrainees();
        List<TotalMarks> savedTotalMarks = new ArrayList<>();

        for( Trainee tempTrainee : trainees ){

            List<TaskEvaluation> taskEvaluation = taskEvaluateRepo.findByTrainee(tempTrainee);
            ManagerEvaluation managerEvaluation = managerEvaluationRepo.findByTrainee(tempTrainee);
            HREvaluation hrEvaluation = hrEvaluationRepo.findByTrainee(tempTrainee);

            Double dailyTask = 0.0 , miniProject = 0.0 , midTerm = 0.0 , finalProject = 0.0;

            for( TaskEvaluation regularTasks : taskEvaluation){

                if( regularTasks.getType().equalsIgnoreCase("Daily Task") )
                    dailyTask += regularTasks.getTotalMarks();
                else if (regularTasks.getType().equalsIgnoreCase("Mini Project"))
                    miniProject += regularTasks.getTotalMarks();
                else if (regularTasks.getType().equalsIgnoreCase("Mid Term"))
                    midTerm += regularTasks.getTotalMarks();
                else
                    finalProject += regularTasks.getTotalMarks();

            }
//-------------------------------- add final project marks -----------------------------------//
            TotalMarks newTotalMarks = TotalMarks.builder()
                    .dailyTask(dailyTask)
                    .midTerm(midTerm)
                    .miniProject(miniProject)
                    .finalProject(finalProject)
                    .managerEvaluation(managerEvaluation.getTotalMarks())
                    .hrEvaluation(hrEvaluation.getTotalMarks())
                    .totalMarks(
                                (dailyTask * weights.getDailyTask() / 100) +
                                (miniProject * weights.getMiniProject() / 100) +
                                (midTerm * weights.getMidTerm() / 100) +
                                (finalProject * weights.getFinalProject() / 100) +
                                (managerEvaluation.getTotalMarks() * weights.getManagerEvaluation() / 100) +
                                (hrEvaluation.getTotalMarks() * weights.getHrEvaluation() / 100)
                    )
                    .trainee(tempTrainee)
                    .build();
            savedTotalMarks.add( finalMarksRepo.save(newTotalMarks) );
        }

        return new ResponseEntity<>(savedTotalMarks, HttpStatus.CREATED );
    }
}
