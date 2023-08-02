package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.MarkEntity;
import com.bjit.tss.entity.ResultEntity;
import com.bjit.tss.model.ResultModel;
import com.bjit.tss.repository.MarkRepository;
import com.bjit.tss.repository.ResultRepository;
import com.bjit.tss.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ResultServiceImplementation implements ResultService {
    private final ResultRepository resultRepository;
    private final MarkRepository markRepository;

    @Override
    public ResponseEntity<Object> saveResult(ResultModel resultModel) {  //autosave
        List<MarkEntity> markEntities = markRepository.findAll();

        if (markEntities.isEmpty()) {
            return ResponseEntity.badRequest().body("No marks found");
        }

        Map<Long, List<MarkEntity>> marksByApplicantId = markEntities.stream()
                .collect(Collectors.groupingBy(MarkEntity::getApplicantId));

        List<ResultEntity> results = new ArrayList<>();

        for (List<MarkEntity> marks : marksByApplicantId.values()) {
            if (marks.size() >= 2) {
                double sum = marks.stream()
                        .mapToDouble(MarkEntity::getMark)
                        .sum();

                String circularTitle = marks.get(0).getCircular();

                ResultEntity resultEntity = new ResultEntity();
                resultEntity.setScore(sum);
                resultEntity.setApplicantId(marks.get(0).getApplicantId());
                resultEntity.setCircularTitle(circularTitle);

                results.add(resultEntity);
            }
        }

        if (results.isEmpty()) {
            return ResponseEntity.badRequest().body("No eligible applicants found");
        }

        List<ResultEntity> savedResults = resultRepository.saveAll(results);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedResults);
    }


    @Override
    public ResponseEntity<Object> updateResult(Long resultId, ResultModel resultModel) {
        Optional<ResultEntity> resultEntityOptional = resultRepository.findById(resultId);

        if (resultEntityOptional.isPresent()) {
            ResultEntity existingResult = resultEntityOptional.get();
            Long applicantId = existingResult.getApplicantId();

            List<MarkEntity> markEntities = markRepository.findByApplicantId(applicantId);

            if (markEntities.size() < 1) {
                return ResponseEntity.badRequest().body("No result is found for this ID: " + applicantId);
            }

            // Calculate the sum of marks
            double sum = markEntities.stream()
                    .mapToDouble(MarkEntity::getMark)
                    .sum();

            existingResult.setScore(sum);

            ResultEntity updatedResult = resultRepository.save(existingResult);
            return ResponseEntity.ok(updatedResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getResultById(Long resultId) {
        Optional<ResultEntity> resultEntityOptional = resultRepository.findById(resultId);

        if (resultEntityOptional.isPresent()) {
            ResultEntity resultEntity = resultEntityOptional.get();
            return ResponseEntity.ok(resultEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getAllResults() {
        List<ResultEntity> resultEntities = resultRepository.findAll();
        return ResponseEntity.ok(resultEntities);
    }

    @Override
    public ResponseEntity<Object> getResultsByApplicantId(Long applicantId) {
        List<ResultEntity> resultEntities = resultRepository.findByApplicantId(applicantId);
        if (resultEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultEntities);
    }

    @Override
    public ResponseEntity<Object> getResultsByCircularTitle(String circularTitle) {
        List<ResultEntity> resultEntities = resultRepository.findByCircularTitle(circularTitle);
        if (resultEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultEntities);
    }

    @Override
    public ResponseEntity<Object> deleteResult(Long resultId) {
        Optional<ResultEntity> resultEntityOptional = resultRepository.findById(resultId);

        if (resultEntityOptional.isPresent()) {
            resultRepository.deleteById(resultId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

