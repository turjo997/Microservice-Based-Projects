package com.bjit.tss.service;

import com.bjit.tss.model.EvaluatorModel;
import org.springframework.http.ResponseEntity;

public interface EvaluatorService {

    ResponseEntity<Object> createEvaluator(EvaluatorModel evaluatorModel);

    ResponseEntity<Object> updateEvaluator(Long evaluatorId, EvaluatorModel evaluatorModel);

    ResponseEntity<Object> deleteEvaluator(Long evaluatorId);

    ResponseEntity<Object> getAllEvaluators();

    ResponseEntity<Object> getEvaluatorById(Long evaluatorId);
}

