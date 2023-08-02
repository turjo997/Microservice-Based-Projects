package com.bjit.tss.service;

import com.bjit.tss.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface ResultService {
    ResponseEntity<Object> saveResult(ResultModel resultModel);

    ResponseEntity<Object> getResultById(Long resultId);

    ResponseEntity<Object> getAllResults();

    ResponseEntity<Object> getResultsByApplicantId(Long applicantId);

    ResponseEntity<Object> getResultsByCircularTitle(String circularTitle);

    ResponseEntity<Object> deleteResult(Long resultId);
    ResponseEntity<Object> updateResult(Long resultId, ResultModel resultModel);
}

