package com.bjit.TraineeSelection.service;

import com.bjit.TraineeSelection.entity.Marks;
import com.bjit.TraineeSelection.model.MarksDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EvaluatorService {
    ResponseEntity<Object> uploadWrittenMark(Long paperCode, MarksDto marksDto);

    ResponseEntity<Object> getEvaluatorId(String email);

    List<Marks> getMarksByEvaluatorId(Long evaluatorId);

}
