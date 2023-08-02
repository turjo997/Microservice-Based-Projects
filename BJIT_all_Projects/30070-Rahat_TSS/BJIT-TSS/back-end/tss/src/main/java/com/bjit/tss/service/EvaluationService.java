package com.bjit.tss.service;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.AssignAnswerSheetRequest;
import com.bjit.tss.model.request.UploadMarkRequest;
import com.bjit.tss.model.request.UploadWrittenMarkRequest;
import com.bjit.tss.model.request.RoundCandidatesRequest;
import org.springframework.http.ResponseEntity;

public interface EvaluationService {

    ResponseEntity<ApiResponse<?>> assignAnswerSheet(AssignAnswerSheetRequest assignAnswerSheetRequest);

    ResponseEntity<ApiResponse<?>> uploadWrittenMark(UploadWrittenMarkRequest uploadWrittenMarkRequest);

    ResponseEntity<ApiResponse<?>> uploadAptitudeMark(UploadMarkRequest uploadMarkRequest);

    ResponseEntity<ApiResponse<?>> uploadTechnicalMark(UploadMarkRequest uploadMarkRequest);

    ResponseEntity<ApiResponse<?>> uploadHrVivaMark(UploadMarkRequest uploadMarkRequest);

    ResponseEntity<ApiResponse<?>> getRoundPassedSpecific(RoundCandidatesRequest roundCandidatesRequest);
}