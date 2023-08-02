package com.bjit.TraineeSelection.service.impl;

import com.bjit.TraineeSelection.entity.*;
import com.bjit.TraineeSelection.model.MarksDto;
import com.bjit.TraineeSelection.repository.AssignEvaluatorRepository;
import com.bjit.TraineeSelection.repository.EvaluatorRepository;
import com.bjit.TraineeSelection.repository.MarkRepository;
import com.bjit.TraineeSelection.service.EvaluatorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluatorServiceImpl implements EvaluatorService {

    private final EvaluatorRepository evaluatorRepository;
    private final AssignEvaluatorRepository assignEvaluatorRepository;
    private final MarkRepository markRepository;

    @Override
    public ResponseEntity<Object> uploadWrittenMark(Long paperCode, MarksDto marksDto) {
        Marks marks1 = markRepository.findMarksByPaperCode(paperCode);

        Long currentStatus = marks1.getStatus();

        if (currentStatus == 1) {
            Long writtenMarks = marksDto.getWrittenMarks();
            if (writtenMarks != null && writtenMarks >= 0) {
                marks1.setWrittenMarks(writtenMarks);

                if (writtenMarks > 50) {
                    marks1.setStatus(currentStatus + 1);
                }
            }
        } else {
            return new ResponseEntity<>("Mark Upload Failed", HttpStatus.BAD_REQUEST);
        }


        Marks savedMarks = markRepository.save(marks1);
        return new ResponseEntity<>("Written Mark Uploaded", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getEvaluatorId(String email) {
        EvaluatorEntity evaluatorEntity = evaluatorRepository.findByEmail(email);
        Long evaluatorId = evaluatorEntity.getEvaluatorId();
        return new ResponseEntity<>(evaluatorId, HttpStatus.OK);
    }




    @Override
    public List<Marks> getMarksByEvaluatorId(Long evaluatorId) {
        AssignEvaluator assignEvaluator = assignEvaluatorRepository.findByEvaluatorId(evaluatorId);
        if (assignEvaluator != null) {
            Long circularId = assignEvaluator.getCircularId();
            return markRepository.findByCircularId(circularId);
        } else {
            throw new EntityNotFoundException("Evaluator with ID " + evaluatorId + " not found.");
        }
    }


}



