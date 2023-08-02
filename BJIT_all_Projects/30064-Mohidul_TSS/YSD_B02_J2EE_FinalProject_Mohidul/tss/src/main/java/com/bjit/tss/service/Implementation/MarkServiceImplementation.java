package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.ExamEntity;
import com.bjit.tss.entity.MarkEntity;
import com.bjit.tss.exceptions.NotFoundException;
import com.bjit.tss.model.MarkModel;
import com.bjit.tss.repository.AdmitcardRepository;
import com.bjit.tss.repository.ExamRepository;
import com.bjit.tss.repository.MarkRepository;
import com.bjit.tss.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarkServiceImplementation implements MarkService {

    private final MarkRepository markRepository;
    private final ExamRepository examRepository;
    private final AdmitcardRepository admitcardRepository;

    @Override
    public ResponseEntity<Object> saveMark(MarkModel markModel) {
        Long examId = markModel.getExam().getExamId();
        Optional<ExamEntity> examEntityOptional = examRepository.findById(examId);

        if (examEntityOptional.isEmpty()) {
            throw new NotFoundException("Exam not found");
        }

        ExamEntity examEntity = examEntityOptional.get();

        Long applicantId = markModel.getApplicantId();

        // Check if a mark already exists for the given exam and applicant
        Optional<MarkEntity> existingMarkOptional = markRepository.findByExamAndApplicantId(examEntity, applicantId);
        if (existingMarkOptional.isPresent()) {
            MarkEntity existingMark = existingMarkOptional.get();
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A mark already exists for the given exam and applicant. Existing mark: " + existingMark);
        }

        MarkEntity markEntity = new MarkEntity();
        markEntity.setExam(examEntity);
        markEntity.setCircular(markModel.getCircular());
        markEntity.setApplicantId(applicantId);
        markEntity.setMark(markModel.getMark());

        MarkEntity savedMark = markRepository.save(markEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMark);
    }


    @Override
    public ResponseEntity<Object> updateMark(Long markId, MarkModel markModel) {
        Optional<MarkEntity> markEntityOptional = markRepository.findById(markId);

        if (markEntityOptional.isPresent()) {
            MarkEntity markEntity = markEntityOptional.get();

            markEntity.setCircular(markModel.getCircular());
            markEntity.setApplicantId(markModel.getApplicantId());
            markEntity.setMark(markModel.getMark());

            MarkEntity updatedMark = markRepository.save(markEntity);

            return ResponseEntity.ok(updatedMark);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<Object> getMarkById(Long markId) {
        Optional<MarkEntity> markEntityOptional = markRepository.findById(markId);

        if (markEntityOptional.isPresent()) {
            MarkEntity markEntity = markEntityOptional.get();
            return ResponseEntity.ok(markEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getAllMarks() {
        List<MarkEntity> markEntities = markRepository.findAll();
        return ResponseEntity.ok(markEntities);
    }

    @Override
    public ResponseEntity<Object> getMarkByApplicantId(Long applicantId) {
        List<MarkEntity> markEntities = markRepository.findByApplicantId(applicantId);
        if (markEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(markEntities);
    }

    @Override
    public ResponseEntity<Object> getMarkByCircular(String circular) {
        List<MarkEntity> markEntities = markRepository.findByCircular(circular);
        if (markEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(markEntities);
    }

    @Override
    public ResponseEntity<Object> deleteMark(Long markId) {
        Optional<MarkEntity> markEntityOptional = markRepository.findById(markId);

        if (markEntityOptional.isPresent()) {
            MarkEntity existingMark = markEntityOptional.get();
            markRepository.delete(existingMark);
            return ResponseEntity.ok(existingMark);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
