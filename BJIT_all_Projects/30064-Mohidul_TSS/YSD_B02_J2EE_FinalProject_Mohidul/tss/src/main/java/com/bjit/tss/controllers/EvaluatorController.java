package com.bjit.tss.controllers;

import com.bjit.tss.model.EvaluatorModel;
import com.bjit.tss.service.EvaluatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evaluator")
@RequiredArgsConstructor
public class EvaluatorController {

    private final EvaluatorService evaluatorService;

    @PostMapping
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> createEvaluator(@RequestBody EvaluatorModel evaluatorModel) {
        return evaluatorService.createEvaluator(evaluatorModel);
    }

    @PutMapping("/{evaluatorId}")
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> updateEvaluator(@PathVariable Long evaluatorId, @RequestBody EvaluatorModel evaluatorModel) {
        return evaluatorService.updateEvaluator(evaluatorId, evaluatorModel);
    }

    @DeleteMapping("/{evaluatorId}")
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> deleteEvaluator(@PathVariable Long evaluatorId) {
        return evaluatorService.deleteEvaluator(evaluatorId);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> getAllEvaluators() {
        return evaluatorService.getAllEvaluators();
    }

    @GetMapping("/{evaluatorId}")
    @PreAuthorize("hasAnyRole( 'ADMIN', 'EVALUATOR')")
    public ResponseEntity<Object> getEvaluatorById(@PathVariable Long evaluatorId) {
        return evaluatorService.getEvaluatorById(evaluatorId);
    }
}

