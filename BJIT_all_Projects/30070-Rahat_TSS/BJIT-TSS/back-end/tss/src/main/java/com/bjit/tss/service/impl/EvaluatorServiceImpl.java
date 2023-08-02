package com.bjit.tss.service.impl;

import com.bjit.tss.entity.EvaluatorInfo;
import com.bjit.tss.entity.HiddenCodeInfo;
import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.mapper.HiddenCodeResponseMapper;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.response.HiddenCodeCandidateResponse;
import com.bjit.tss.model.response.ListResponse;
import com.bjit.tss.repository.EvaluatorRepository;
import com.bjit.tss.repository.HiddenCodeRepository;
import com.bjit.tss.service.EvaluatorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluatorServiceImpl implements EvaluatorService {
    private final EvaluatorRepository evaluatorRepository;
    private final HiddenCodeRepository hiddenCodeRepository;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> getAllEvaluator() {
        List<EvaluatorInfo> evaluatorInfo = evaluatorRepository.findAll();

        if (evaluatorInfo.size() == 0 ){
            ListResponse<?> listResponse = ListResponse.builder()
                    .dataLength(0)
                    .listResponse(evaluatorInfo)
                    .build();
            return ApiResponseMapper.mapToResponseEntityOK(listResponse, "Evaluator List is empty");
        }
        else {

            ListResponse<?> listResponse = ListResponse.builder()
                    .dataLength(evaluatorInfo.size())
                    .listResponse(evaluatorInfo)
                    .build();
            return ApiResponseMapper.mapToResponseEntityOK(listResponse, "Evaluator List");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> getAssignedCandidate(Long evaluatorId) {
        List<HiddenCodeInfo> hiddenCodeInfos = hiddenCodeRepository.findAllByCandidateMarksWrittenMarksEvaluatorInfoEvaluatorIdAndCandidateMarksExamineeInfoCourseInfoIsAvailable(evaluatorId, true);
        List<HiddenCodeCandidateResponse> hiddenCodeCandidateResponses = hiddenCodeInfos.stream().map(HiddenCodeResponseMapper::mapToHiddenCodeResponse).toList();

        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(hiddenCodeCandidateResponses.size())
                .listResponse(hiddenCodeCandidateResponses)
                .build();

        return ApiResponseMapper.mapToResponseEntityOK(listResponse, "List of assigned candidates to evaluator");
    }
}