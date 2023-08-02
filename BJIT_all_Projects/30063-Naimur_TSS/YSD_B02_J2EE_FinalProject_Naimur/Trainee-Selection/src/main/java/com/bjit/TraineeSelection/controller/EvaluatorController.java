package com.bjit.TraineeSelection.controller;

import com.bjit.TraineeSelection.entity.Marks;
import com.bjit.TraineeSelection.model.MarksDto;
import com.bjit.TraineeSelection.service.EvaluatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/evaluator")
@RequiredArgsConstructor
public class EvaluatorController {

    private final EvaluatorService evaluatorService;

    @PostMapping("/writtenMark/{paperCode}")
    public ResponseEntity<Object> uploadWrittenMarks(@PathVariable Long paperCode, @RequestBody MarksDto marksDto) {
        return evaluatorService.uploadWrittenMark(paperCode, marksDto);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> getEvaluatorId(@PathVariable String email) {
        return this.evaluatorService.getEvaluatorId(email);
    }

    @GetMapping("/marks/{evaluatorId}")
    public ResponseEntity<List<Marks>> getMarksByEvaluatorId(@PathVariable Long evaluatorId) {
        List<Marks> marks = evaluatorService.getMarksByEvaluatorId(evaluatorId);
        if (marks.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(marks);
        }
    }
}

