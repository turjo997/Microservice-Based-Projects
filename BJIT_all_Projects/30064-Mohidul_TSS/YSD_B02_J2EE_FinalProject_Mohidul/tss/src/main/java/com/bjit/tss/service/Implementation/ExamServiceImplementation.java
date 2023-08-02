package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.ExamEntity;
import com.bjit.tss.model.ExamModel;
import com.bjit.tss.repository.ExamRepository;
import com.bjit.tss.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamServiceImplementation implements ExamService {

    private final ExamRepository examRepository;

    @Override
    public ResponseEntity<Object> createExam(ExamModel examModel) {
        ExamEntity examEntity = new ExamEntity();
        examEntity.setExamName(examModel.getExamName());
        examEntity.setExamCode(examModel.getExamCode());

        ExamEntity savedExam = examRepository.save(examEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExam);
    }

    @Override
    public ResponseEntity<Object> updateExamByExamCode(Long examCode, ExamModel examModel) {
        ExamEntity existingExam = examRepository.findByExamCode(examCode);
        if (existingExam != null) {
            existingExam.setExamName(examModel.getExamName());
            existingExam.setExamCode(examModel.getExamCode());
            examRepository.save(existingExam);
            return ResponseEntity.ok(existingExam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> deleteExamByExamCode(Long examCode) {
        ExamEntity existingExam = examRepository.findByExamCode(examCode);
        if (existingExam != null) {
            examRepository.delete(existingExam);
            return ResponseEntity.ok(existingExam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getAllExams() {
        List<ExamEntity> exams = examRepository.findAll();
        return ResponseEntity.ok(exams);
    }

    @Override
    public ResponseEntity<Object> getExamById(Long examId) {
        Optional<ExamEntity> optionalExam = examRepository.findById(examId);
        if (optionalExam.isPresent()) {
            ExamEntity exam = optionalExam.get();
            return ResponseEntity.ok(exam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getExamByExamCode(Long examCode) {
        ExamEntity exam = examRepository.findByExamCode(examCode);
        if (exam != null) {
            return ResponseEntity.ok(exam);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
