package com.bjit.tss.service.impl;

import com.bjit.tss.entity.DataStorage;
import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.QuestionNumberRequest;
import com.bjit.tss.repository.DataStorageRepository;
import com.bjit.tss.service.QuestionNumberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionNumberServiceImpl implements QuestionNumberService {

    private final DataStorageRepository dataStorageRepository;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> setWrittenQuestionNumber(QuestionNumberRequest questionNumberRequest) {
        Optional<DataStorage> dataStorage1 = dataStorageRepository.findByDataKey("WrittenQuestionNumber");
        if (dataStorage1.isPresent()) {
            dataStorage1.get().setDataValue(String.valueOf(questionNumberRequest.getQuestionNumbers()));
            DataStorage saved = dataStorageRepository.save(dataStorage1.get());
            return ApiResponseMapper.mapToResponseEntityCreated(saved);
        } else {
            DataStorage dataStorage = DataStorage.builder()
                    .dataKey(questionNumberRequest.getQuestionType())
                    .dataValue(String.valueOf(questionNumberRequest.getQuestionNumbers()))
                    .build();
            DataStorage saved = dataStorageRepository.save(dataStorage);
            return ApiResponseMapper.mapToResponseEntityCreated(saved);
        }
    }
}