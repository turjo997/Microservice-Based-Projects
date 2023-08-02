package com.bjit.TraineeSelection.service;

import com.bjit.TraineeSelection.entity.*;
import com.bjit.TraineeSelection.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
     ResponseEntity<Object> createCircular(CircularDto circularDto);


     ResponseEntity<Object>  approveApplicant(Long applicationId);

     ResponseEntity<Object> showAll();

     ResponseEntity<Object> updateCircular(CircularDto circularDto);

     ResponseEntity<Object> uploadMark(MarksDto marksDto);

    ResponseEntity<Object> updateMark(Long applicantId, MarksDto marksDto);

    ResponseEntity<Object> viewAllApplicationsByCircular(Long circularId);

    ResponseEntity<Object> showAllApplication();

//    ResponseEntity<Object> generateApprovedApplicantsPDF();

    ResponseEntity<byte[]> getAdmitCard(Long applicantId);

    ResponseEntity<List<Circular>> getCirculars();

    ResponseEntity<Object> getCircularByIds(Long circularId);


    ResponseEntity<List<Application>> getAllApprovedApplication();

    ResponseEntity<Object> registerEvaluator(EvaluatorDto evaluatorDto);

    ResponseEntity<Object> showAllEvaluators();

    ResponseEntity<Object> getEvaluatorById(Long evaluatorId);

    ResponseEntity<Object> assignEvaluator(AssignEvaluatorDto assignEvaluatorDto);

    ResponseEntity<Object> findApplicant(Long applicantId);


    ResponseEntity<List<Marks>> showAllFinalCandidate();

//    String sendMail(Mail mail);
}
