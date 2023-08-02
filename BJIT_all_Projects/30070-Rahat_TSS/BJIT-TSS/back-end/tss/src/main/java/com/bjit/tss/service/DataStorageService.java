package com.bjit.tss.service;

import com.bjit.tss.model.request.DataStorageGetRequest;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.DataStorageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DataStorageService {
    ResponseEntity<ApiResponse<?>> setDataStorage(List<DataStorageRequest> dataStorageRequestList);

    ResponseEntity<ApiResponse<?>> getDataStorage(List<DataStorageGetRequest> dataStorageGetRequests);

    ResponseEntity<ApiResponse<?>> setDataStorageInit(List<DataStorageRequest> dataStorageRequestList) ;

    }