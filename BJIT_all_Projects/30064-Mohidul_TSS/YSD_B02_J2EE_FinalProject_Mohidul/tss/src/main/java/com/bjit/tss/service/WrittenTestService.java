package com.bjit.tss.service;

import com.bjit.tss.model.WrittenTestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface WrittenTestService {
    ResponseEntity<Object> getWrittenTestById(Long writtenTestId);

    ResponseEntity<Object> getAllWrittenTest();

    ResponseEntity<Object> createWrittenTest(WrittenTestModel writtenTestModel);

    ResponseEntity<Object> updateWrittenTest(Long writtenTestId, WrittenTestModel writtenTestModel);

    ResponseEntity<Object> deleteWrittenTest(Long writtenTestId);

    ResponseEntity<Object> autoCreateWrittenTest();

    ResponseEntity<Object> updateWrittenTest(Long hiddenCode, Double mark);

    ResponseEntity<Object> uploadWrittenTestByHiddenCode(MultipartFile file);

    ResponseEntity<Object> getWrittenTestByApplicantId(Long applicantId);
}

