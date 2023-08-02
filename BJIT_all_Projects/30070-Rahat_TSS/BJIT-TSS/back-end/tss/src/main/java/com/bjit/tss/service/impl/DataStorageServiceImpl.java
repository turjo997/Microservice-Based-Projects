package com.bjit.tss.service.impl;

import com.bjit.tss.entity.DataStorage;
import com.bjit.tss.exception.DataStorageException;
import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.model.request.DataStorageGetRequest;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.DataStorageRequest;
import com.bjit.tss.model.response.ListResponse;
import com.bjit.tss.repository.DataStorageRepository;
import com.bjit.tss.service.DataStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataStorageServiceImpl implements DataStorageService {

    private final DataStorageRepository dataStorageRepository;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> setDataStorage(List<DataStorageRequest> dataStorageRequestList) {
        if (dataStorageRequestList.size() == 0) {
            throw new DataStorageException("Invalid Request");
        }
        List<DataStorage> dataStorageList = dataStorageRequestList.stream().map((request) -> {
            Optional<DataStorage> dataStorage1 = dataStorageRepository.findByDataKey(request.getDataKey());
            if (dataStorage1.isPresent()) {
                dataStorage1.get().setDataValue(request.getDataValue());
                return dataStorageRepository.save(dataStorage1.get());
            } else {
                DataStorage dataStorage = DataStorage.builder()
                        .dataKey(request.getDataKey())
                        .dataValue(request.getDataValue())
                        .build();
                return dataStorageRepository.save(dataStorage);
            }
        }).toList();
        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(dataStorageList.size())
                .listResponse(dataStorageList)
                .build();

        return ApiResponseMapper.mapToResponseEntityOK(listResponse, "Data updated successfully.");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> getDataStorage(List<DataStorageGetRequest> dataStorageGetRequests) {
        if (dataStorageGetRequests.size() == 0) {
            throw new DataStorageException("Invalid Request");
        }
        List<DataStorage> dataStorageList = dataStorageGetRequests.stream().map((request) -> {
            Optional<DataStorage> dataStorage1 = dataStorageRepository.findByDataKey(request.getDataKey());
            if (dataStorage1.isPresent()) {
                return dataStorage1.get();
            } else {
                throw new DataStorageException("Invalid request.");
            }
        }).toList();
        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(dataStorageList.size())
                .listResponse(dataStorageList)
                .build();

        return ApiResponseMapper.mapToResponseEntityOK(listResponse, "Getting data successfully.");
    }
    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> setDataStorageInit(List<DataStorageRequest> dataStorageRequestList) {
        if (dataStorageRequestList.size() == 0) {
            throw new DataStorageException("Invalid Request");
        }
        List<DataStorage> dataStorageList = dataStorageRequestList.stream().map((request) -> {
            Optional<DataStorage> dataStorage1 = dataStorageRepository.findByDataKey(request.getDataKey());
            if (dataStorage1.isPresent()) {
                return null;
            } else {
                DataStorage dataStorage = DataStorage.builder()
                        .dataKey(request.getDataKey())
                        .dataValue(request.getDataValue())
                        .build();
                return dataStorageRepository.save(dataStorage);
            }
        }).toList();
        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(dataStorageList.size())
                .listResponse(dataStorageList)
                .build();

        return ApiResponseMapper.mapToResponseEntityOK(listResponse);
    }
}
