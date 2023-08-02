package com.bjit.tss.controller;

import com.bjit.tss.model.request.DataStorageGetRequest;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.DataStorageRequest;
import com.bjit.tss.service.DataStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data-storage")
public class DataStorageController {

    private final DataStorageService dataStorageService;

    @PostMapping("/set")
    public ResponseEntity<ApiResponse<?>> setDataStorage(@Valid @RequestBody List<DataStorageRequest> dataStorageRequestList) {
        return dataStorageService.setDataStorage(dataStorageRequestList);
    }
    @PostMapping("/get")
    public ResponseEntity<ApiResponse<?>> getDataStorage(@Valid @RequestBody List<DataStorageGetRequest> dataStorageGetRequests) {
        return dataStorageService.getDataStorage(dataStorageGetRequests);
    }

}