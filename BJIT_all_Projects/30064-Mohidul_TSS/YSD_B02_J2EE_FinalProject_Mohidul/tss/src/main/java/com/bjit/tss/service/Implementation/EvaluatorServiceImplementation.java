package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.EvaluatorEntity;
import com.bjit.tss.model.EvaluatorModel;
import com.bjit.tss.repository.EvaluatorRepository;
import com.bjit.tss.service.EvaluatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvaluatorServiceImplementation implements EvaluatorService {

    private final EvaluatorRepository evaluatorRepository;

    @Override
    public ResponseEntity<Object> createEvaluator(EvaluatorModel evaluatorModel) {
        EvaluatorEntity evaluatorEntity = new EvaluatorEntity();
        evaluatorEntity.setEvaluatorName(evaluatorModel.getEvaluatorName());
        evaluatorEntity.setSpecialization(evaluatorModel.getSpecialization());
        evaluatorEntity.setTitle(evaluatorModel.getTitle());

        EvaluatorEntity savedEvaluator = evaluatorRepository.save(evaluatorEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvaluator);
    }

    @Override
    public ResponseEntity<Object> updateEvaluator(Long evaluatorId, EvaluatorModel evaluatorModel) {
        Optional<EvaluatorEntity> optionalEvaluator = evaluatorRepository.findById(evaluatorId);
        if (optionalEvaluator.isPresent()) {
            EvaluatorEntity existingEvaluator = optionalEvaluator.get();
            existingEvaluator.setEvaluatorName(evaluatorModel.getEvaluatorName());
            existingEvaluator.setSpecialization(evaluatorModel.getSpecialization());
            existingEvaluator.setTitle(evaluatorModel.getTitle());

            evaluatorRepository.save(existingEvaluator);
            return ResponseEntity.ok(existingEvaluator);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> deleteEvaluator(Long evaluatorId) {
        Optional<EvaluatorEntity> optionalEvaluator = evaluatorRepository.findById(evaluatorId);
        if (optionalEvaluator.isPresent()) {
            EvaluatorEntity existingEvaluator = optionalEvaluator.get();
            evaluatorRepository.delete(existingEvaluator);
            return ResponseEntity.ok(existingEvaluator);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getAllEvaluators() {
        List<EvaluatorEntity> evaluators = evaluatorRepository.findAll();
        return ResponseEntity.ok(evaluators);
    }

    @Override
    public ResponseEntity<Object> getEvaluatorById(Long evaluatorId) {
        Optional<EvaluatorEntity> optionalEvaluator = evaluatorRepository.findById(evaluatorId);
        if (optionalEvaluator.isPresent()) {
            EvaluatorEntity evaluator = optionalEvaluator.get();
            return ResponseEntity.ok(evaluator);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
