package com.bjit.tss.controller;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.AssignAnswerSheetRequest;
import com.bjit.tss.model.request.UploadMarkRequest;
import com.bjit.tss.model.request.UploadWrittenMarkRequest;
import com.bjit.tss.model.request.RoundCandidatesRequest;
import com.bjit.tss.service.EvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluation")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping("/assign-answer")
    public ResponseEntity<ApiResponse<?>> assignAnswerSheet(@Valid @RequestBody AssignAnswerSheetRequest assignAnswerSheetRequest) {
        return evaluationService.assignAnswerSheet(assignAnswerSheetRequest);
    }

    @PostMapping("/upload-mark/written")
    public ResponseEntity<ApiResponse<?>> uploadWrittenMark(@Valid @RequestBody UploadWrittenMarkRequest uploadWrittenMarkRequest) {
        return evaluationService.uploadWrittenMark(uploadWrittenMarkRequest);
    }

    @PostMapping("/upload-mark/aptitude")
    public ResponseEntity<ApiResponse<?>> uploadAptitudeMark(@RequestBody UploadMarkRequest UploadMarkRequest) {
        return evaluationService.uploadAptitudeMark(UploadMarkRequest);
    }

    @PostMapping("upload-mark/technical")
    public ResponseEntity<ApiResponse<?>> uploadTechnicalMark(@RequestBody UploadMarkRequest UploadMarkRequest) {
        return evaluationService.uploadTechnicalMark(UploadMarkRequest);
    }

    @PostMapping("/upload-mark/hr-viva")
    public ResponseEntity<ApiResponse<?>> uploadHrVivaMark(@RequestBody UploadMarkRequest UploadMarkRequest) {
        return evaluationService.uploadHrVivaMark(UploadMarkRequest);
    }

    @PostMapping("/passed-round")
    public ResponseEntity<ApiResponse<?>> getRoundPassedSpecific(@RequestBody RoundCandidatesRequest roundCandidatesRequest){
        return evaluationService.getRoundPassedSpecific(roundCandidatesRequest);
    }
}