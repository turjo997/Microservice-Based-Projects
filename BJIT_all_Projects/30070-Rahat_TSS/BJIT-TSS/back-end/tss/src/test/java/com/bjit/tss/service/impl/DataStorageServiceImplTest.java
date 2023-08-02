package com.bjit.tss.service.impl;

import com.bjit.tss.entity.DataStorage;
import com.bjit.tss.exception.DataStorageException;
import com.bjit.tss.model.request.DataStorageGetRequest;
import com.bjit.tss.model.request.DataStorageRequest;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.response.ListResponse;
import com.bjit.tss.repository.DataStorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DataStorageServiceImplTest {

    @Mock
    private DataStorageRepository dataStorageRepository;

    @InjectMocks
    private DataStorageServiceImpl dataStorageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetDataStorage_Success() {
        List<DataStorageRequest> dataStorageRequestList = new ArrayList<>();
        dataStorageRequestList.add(DataStorageRequest.builder().dataKey("key1").dataValue("value1").build());
        dataStorageRequestList.add(DataStorageRequest.builder().dataKey("key2").dataValue("value2").build());

        when(dataStorageRepository.findByDataKey("key1")).thenReturn(Optional.empty());
        when(dataStorageRepository.findByDataKey("key2")).thenReturn(Optional.empty());
        when(dataStorageRepository.save(any(DataStorage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<ApiResponse<?>> response = dataStorageService.setDataStorage(dataStorageRequestList);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        ListResponse<?> listResponse = (ListResponse<?>) response.getBody().getData();
        assertNotNull(listResponse);
        assertEquals(2, listResponse.getDataLength());
    }

    @Test
    void testSetDataStorage_EmptyRequest() {
        List<DataStorageRequest> dataStorageRequestList = new ArrayList<>();

        assertThrows(DataStorageException.class, () -> dataStorageService.setDataStorage(dataStorageRequestList));
        verify(dataStorageRepository, never()).findByDataKey(any());
        verify(dataStorageRepository, never()).save(any(DataStorage.class));
    }

    @Test
    void testGetDataStorage_Success() {
        List<DataStorageGetRequest> dataStorageGetRequests = new ArrayList<>();
        dataStorageGetRequests.add(DataStorageGetRequest.builder().dataKey("key1").build());
        dataStorageGetRequests.add(DataStorageGetRequest.builder().dataKey("key2").build());

        DataStorage data1 = DataStorage.builder().dataKey("key1").dataValue("value1").build();
        DataStorage data2 = DataStorage.builder().dataKey("key2").dataValue("value2").build();
        when(dataStorageRepository.findByDataKey("key1")).thenReturn(Optional.of(data1));
        when(dataStorageRepository.findByDataKey("key2")).thenReturn(Optional.of(data2));

        ResponseEntity<ApiResponse<?>> response = dataStorageService.getDataStorage(dataStorageGetRequests);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        ListResponse<?> listResponse = (ListResponse<?>) response.getBody().getData();
        assertNotNull(listResponse);
        assertEquals(2, listResponse.getDataLength());
    }

    @Test
    void testGetDataStorage_InvalidRequest() {
        List<DataStorageGetRequest> dataStorageGetRequests = new ArrayList<>();

        assertThrows(DataStorageException.class, () -> dataStorageService.getDataStorage(dataStorageGetRequests));
        verify(dataStorageRepository, never()).findByDataKey(any());
    }
}
