package com.bjit.tss.controller;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.service.EvaluatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluator")
@RequiredArgsConstructor
public class EvaluatorController {

    private final EvaluatorService evaluatorService;

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllEvaluator() {
        return evaluatorService.getAllEvaluator();
    }


    @GetMapping("/assigned-candidates/{evaluatorId}")
    public ResponseEntity<ApiResponse<?>> getAssignedCandidate(@PathVariable Long evaluatorId) {
        return evaluatorService.getAssignedCandidate(evaluatorId);

    }
}
