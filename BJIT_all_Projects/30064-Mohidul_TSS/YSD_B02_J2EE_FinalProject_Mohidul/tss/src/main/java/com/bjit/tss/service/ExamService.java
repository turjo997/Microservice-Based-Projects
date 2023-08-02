package com.bjit.tss.service;


import com.bjit.tss.model.ExamModel;
import org.springframework.http.ResponseEntity;

public interface ExamService {

    ResponseEntity<Object> createExam(ExamModel examModel);

    ResponseEntity<Object> updateExamByExamCode(Long examCode, ExamModel examModel);

    ResponseEntity<Object> deleteExamByExamCode(Long examCode);

    ResponseEntity<Object> getAllExams();

    ResponseEntity<Object> getExamById(Long examId);

    ResponseEntity<Object> getExamByExamCode(Long examCode);
}

