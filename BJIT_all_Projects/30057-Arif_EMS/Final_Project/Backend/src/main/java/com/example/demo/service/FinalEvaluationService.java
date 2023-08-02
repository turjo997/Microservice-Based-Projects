package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class FinalEvaluationService {

    private final ManagerEvaluationRepository managerEvaluationRepo;
    private final HREvaluationRepository hrEvaluationRepo;
    private final TraineeRepository traineeRepo;
    private final FinalProjectCriteriaRepository finalProjectCriteriaRepo;
    private final FinalProjectMarksRepository finalProjectMarkRepo;

    public ResponseEntity<List<FinalProjectCriteria>> addCriteria(Long batchId, List<String> criteria) {

        List<FinalProjectCriteria> recentlyAddedCriteria = new ArrayList<>();

        for( String thisCriteria : criteria ){
            FinalProjectCriteria newCriteria = FinalProjectCriteria.builder()
                    .batchId(batchId)
                    .criteria(thisCriteria)
                    .build();
            recentlyAddedCriteria.add( finalProjectCriteriaRepo.save(newCriteria) );
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(recentlyAddedCriteria);

    }

    public ResponseEntity<Set<String>> viewAllCriteria(Long batchId) {

        Set<FinalProjectCriteria> criteriaEntities = finalProjectCriteriaRepo.findByBatchId(batchId);
        Set<String> allCriteria = new HashSet<>();

        for( FinalProjectCriteria thisCriteria : criteriaEntities ){
            allCriteria.add( thisCriteria.getCriteria() );
        }

        return ResponseEntity.status(HttpStatus.OK).body(allCriteria);

    }

    public ResponseEntity<List<FinalProjectMarkModel>> addFinalMarks(Long traineeId, List<FinalProjectMarkModel> criteriaMarks) {

        for( FinalProjectMarkModel thisMark : criteriaMarks){
            FinalProjectMarks finalMark = FinalProjectMarks.builder()
                    .traineeId(traineeId)
                    .criteria(thisMark.getCriteria())
                    .mark(thisMark.getMark())
                    .build();
            finalProjectMarkRepo.save(finalMark);
        }

        return ResponseEntity.status(HttpStatus.OK).body(criteriaMarks);
    }
    
    public ResponseEntity<HREvaluation> createHrEvaluation(HREvaluationModel hrEvaluation , Long traineeId){

        Trainee whichTrainee = traineeRepo.findById(traineeId).orElseThrow();
        
        HREvaluation newHrEvaluation = HREvaluation.builder()
                .aptitudeEvaluation(hrEvaluation.getAptitudeEvaluation())
                .hrEvaluation(hrEvaluation.getHrEvaluation())
                .totalMarks(
                        hrEvaluation.getAptitudeEvaluation() +
                        hrEvaluation.getHrEvaluation()
                )
                .trainee(whichTrainee)
                .build();
        
        HREvaluation savedHrEvaluation = hrEvaluationRepo.save(newHrEvaluation);
        
        return new ResponseEntity<>( savedHrEvaluation , HttpStatus.CREATED);
        
    }

    public ResponseEntity<List<HREvaluation>> viewAllHrEvaluations() {

        return new ResponseEntity<>(hrEvaluationRepo.findAll() , HttpStatus.OK);

    }

    public ResponseEntity<HREvaluation> findHrEvaluationByHrEvaluationId(Long hrEvaluationId) {

        return new ResponseEntity<>(hrEvaluationRepo.findById(hrEvaluationId).orElseThrow() , HttpStatus.OK);

    }

    public ResponseEntity<ManagerEvaluation> createManagerEvaluation(ManagerEvaluationModel managerEvaluation , Long traineeId){

        Trainee whichTrainee = traineeRepo.findById(traineeId).orElseThrow();

        ManagerEvaluation newManagerEvaluation = ManagerEvaluation.builder()
                .attendance(managerEvaluation.getAttendance())
                .communicationSkill(managerEvaluation.getCommunicationSkill())
                .languageSkill(managerEvaluation.getLanguageSkill())
                .qualityMindset(managerEvaluation.getQualityMindset())
                .sincerityAndHardWorking(managerEvaluation.getSincerityAndHardWorking())
                .officeRules(managerEvaluation.getOfficeRules())
                .bjitToolsUsages(managerEvaluation.getBjitToolsUsages())
                .totalMarks(
                                managerEvaluation.getAttendance() +
                                managerEvaluation.getCommunicationSkill() +
                                managerEvaluation.getLanguageSkill() +
                                managerEvaluation.getQualityMindset() +
                                managerEvaluation.getSincerityAndHardWorking() +
                                managerEvaluation.getOfficeRules() +
                                managerEvaluation.getBjitToolsUsages()
                )
                .trainee(whichTrainee)
                .build();
        ManagerEvaluation savedManagerEvaluation = managerEvaluationRepo.save(newManagerEvaluation);

        return new ResponseEntity<>(savedManagerEvaluation , HttpStatus.CREATED);

    }

    public ResponseEntity<List<ManagerEvaluation>> viewAllManagerEvaluations() {

        return new ResponseEntity<>( managerEvaluationRepo.findAll() , HttpStatus.OK);

    }

    public ResponseEntity<ManagerEvaluation> findManagerEvaluationByManagerEvaluationId(Long managerEvaluationId) {

        return new ResponseEntity<>( managerEvaluationRepo.findById(managerEvaluationId).orElseThrow() , HttpStatus.OK);

    }

}
