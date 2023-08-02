package com.bjit.tss.service;

import com.bjit.tss.model.MarkModel;
import org.springframework.http.ResponseEntity;

public interface MarkService {
    ResponseEntity<Object> saveMark(MarkModel markModel);
    ResponseEntity<Object> updateMark(Long markId, MarkModel markModel);
    ResponseEntity<Object> getMarkById(Long markId);
    ResponseEntity<Object> getAllMarks();
    ResponseEntity<Object> getMarkByApplicantId(Long applicantId);
    ResponseEntity<Object> getMarkByCircular(String circular);
    ResponseEntity<Object> deleteMark(Long markId);
}
