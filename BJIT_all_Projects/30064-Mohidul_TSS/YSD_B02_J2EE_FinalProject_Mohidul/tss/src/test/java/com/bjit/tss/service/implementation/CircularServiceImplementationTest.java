package com.bjit.tss.service.implementation;

import com.bjit.tss.entity.CircularEntity;
import com.bjit.tss.model.CircularModel;
import com.bjit.tss.repository.CircularRepository;
import com.bjit.tss.service.Implementation.CircularServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CircularServiceImplementationTest {

    @Mock
    private CircularRepository circularRepository;

    @InjectMocks
    private CircularServiceImplementation circularService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCircular() {
        CircularModel circularModel = getSampleCircularModel();
        CircularEntity circularEntity = getSampleCircularEntity();

        when(circularRepository.save(any())).thenReturn(circularEntity);

        ResponseEntity<Object> response = circularService.createCircular(circularModel);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(circularEntity, response.getBody());

        verify(circularRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateCircularWhenCircularExists() {
        Long circularId = 1L;
        CircularModel updatedCircularModel = getSampleCircularModel();

        CircularEntity existingCircular = getSampleCircularEntity();
        existingCircular.setCircularId(circularId);

        when(circularRepository.findById(circularId)).thenReturn(Optional.of(existingCircular));
        when(circularRepository.save(any())).thenReturn(existingCircular);

        ResponseEntity<Object> response = circularService.updateCircular(circularId, updatedCircularModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingCircular, response.getBody());

        verify(circularRepository, times(1)).findById(circularId);
        verify(circularRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateCircularWhenCircularDoesNotExist() {
        Long circularId = 1L;
        CircularModel updatedCircularModel = getSampleCircularModel();

        when(circularRepository.findById(circularId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = circularService.updateCircular(circularId, updatedCircularModel);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(circularRepository, times(1)).findById(circularId);
        verify(circularRepository, never()).save(any());
    }

    @Test
    public void testDeleteCircularWhenCircularExists() {
        Long circularId = 1L;

        CircularEntity existingCircular = getSampleCircularEntity();
        existingCircular.setCircularId(circularId);

        when(circularRepository.findById(circularId)).thenReturn(Optional.of(existingCircular));

        ResponseEntity<Object> response = circularService.deleteCircular(circularId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingCircular, response.getBody());

        verify(circularRepository, times(1)).findById(circularId);
        verify(circularRepository, times(1)).delete(existingCircular);
    }

    @Test
    public void testDeleteCircularWhenCircularDoesNotExist() {
        Long circularId = 1L;

        when(circularRepository.findById(circularId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = circularService.deleteCircular(circularId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(circularRepository, times(1)).findById(circularId);
        verify(circularRepository, never()).delete(any());
    }

    @Test
    public void testGetAllCirculars() {
        List<CircularEntity> circulars = Arrays.asList(getSampleCircularEntity(), getSampleCircularEntity());

        when(circularRepository.findAll()).thenReturn(circulars);

        ResponseEntity<Object> response = circularService.getAllCirculars();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(circulars, response.getBody());

        verify(circularRepository, times(1)).findAll();
    }

    @Test
    public void testGetDetailByIdWhenCircularExists() {
        Long circularId = 1L;
        CircularEntity existingCircular = getSampleCircularEntity();
        existingCircular.setCircularId(circularId);

        when(circularRepository.findById(circularId)).thenReturn(Optional.of(existingCircular));

        ResponseEntity<Object> response = circularService.getDetailById(circularId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingCircular, response.getBody());

        verify(circularRepository, times(1)).findById(circularId);
    }

    @Test
    public void testGetDetailByIdWhenCircularDoesNotExist() {
        Long circularId = 1L;

        when(circularRepository.findById(circularId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = circularService.getDetailById(circularId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(circularRepository, times(1)).findById(circularId);
    }

    private CircularModel getSampleCircularModel() {
        return new CircularModel(
                null,
                "Sample Circular",
                "This is a sample circular description."
        );
    }

    private CircularEntity getSampleCircularEntity() {
        return new CircularEntity(
                null,
                "Sample Circular",
                "This is a sample circular description."
        );
    }
}

