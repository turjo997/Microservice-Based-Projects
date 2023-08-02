package com.bjit.tss.service.implementation;

import com.bjit.tss.entity.ApplicantEntity;
import com.bjit.tss.model.ApplicantModel;
import com.bjit.tss.repository.ApplicantRepository;
import com.bjit.tss.service.Implementation.ApplicantServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ApplicantServiceImplementationTest {

    @Mock
    private ApplicantRepository applicantRepository;

    @InjectMocks
    private ApplicantServiceImplementation applicantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateApplicant() {
        ApplicantModel applicantModel = getSampleApplicantModel();
        ApplicantEntity applicantEntity = getSampleApplicantEntity();

        when(applicantRepository.save(any())).thenReturn(applicantEntity);

        ResponseEntity<Object> response = applicantService.createApplicant(applicantModel);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(applicantEntity, response.getBody());

        verify(applicantRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateApplicantWhenApplicantExists() {
        Long applicantId = 1L;
        ApplicantModel updatedApplicantModel = getSampleApplicantModel();

        ApplicantEntity existingApplicant = getSampleApplicantEntity();
        existingApplicant.setApplicantId(applicantId);

        when(applicantRepository.findById(applicantId)).thenReturn(Optional.of(existingApplicant));
        when(applicantRepository.save(any())).thenReturn(existingApplicant);

        ResponseEntity<Object> response = applicantService.updateApplicant(applicantId, updatedApplicantModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingApplicant, response.getBody());

        verify(applicantRepository, times(1)).findById(applicantId);
        verify(applicantRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateApplicantWhenApplicantDoesNotExist() {
        Long applicantId = 1L;
        ApplicantModel updatedApplicantModel = getSampleApplicantModel();

        when(applicantRepository.findById(applicantId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = applicantService.updateApplicant(applicantId, updatedApplicantModel);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(applicantRepository, times(1)).findById(applicantId);
        verify(applicantRepository, never()).save(any());
    }

    @Test
    public void testDeleteApplicantWhenApplicantExists() {
        Long applicantId = 1L;

        ApplicantEntity existingApplicant = getSampleApplicantEntity();
        existingApplicant.setApplicantId(applicantId);

        when(applicantRepository.findById(applicantId)).thenReturn(Optional.of(existingApplicant));

        ResponseEntity<Object> response = applicantService.deleteApplicant(applicantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingApplicant, response.getBody());

        verify(applicantRepository, times(1)).findById(applicantId);
        verify(applicantRepository, times(1)).delete(existingApplicant);
    }

    @Test
    public void testDeleteApplicantWhenApplicantDoesNotExist() {
        Long applicantId = 1L;

        when(applicantRepository.findById(applicantId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = applicantService.deleteApplicant(applicantId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(applicantRepository, times(1)).findById(applicantId);
        verify(applicantRepository, never()).delete(any());
    }

    @Test
    public void testGetAllApplicants() {
        List<ApplicantEntity> applicants = Arrays.asList(getSampleApplicantEntity(), getSampleApplicantEntity());

        when(applicantRepository.findAll()).thenReturn(applicants);

        ResponseEntity<Object> response = applicantService.getAllApplicants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(applicants, response.getBody());

        verify(applicantRepository, times(1)).findAll();
    }

    @Test
    public void testGetApplicantByIdWhenApplicantExists() {
        Long applicantId = 1L;
        ApplicantEntity existingApplicant = getSampleApplicantEntity();
        existingApplicant.setApplicantId(applicantId);

        when(applicantRepository.findById(applicantId)).thenReturn(Optional.of(existingApplicant));

        ResponseEntity<Object> response = applicantService.getApplicantById(applicantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingApplicant, response.getBody());

        verify(applicantRepository, times(1)).findById(applicantId);
    }

    @Test
    public void testGetApplicantByIdWhenApplicantDoesNotExist() {
        Long applicantId = 1L;

        when(applicantRepository.findById(applicantId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = applicantService.getApplicantById(applicantId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(applicantRepository, times(1)).findById(applicantId);
    }

    @Test
    public void testGetAllApplicantsWhenNoApplicantsExist() {
        List<ApplicantEntity> emptyList = Collections.emptyList();

        when(applicantRepository.findAll()).thenReturn(emptyList);

        ResponseEntity<Object> response = applicantService.getAllApplicants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyList, response.getBody());

        verify(applicantRepository, times(1)).findAll();
    }

    @Test
    public void testGetApplicantByInvalidId() {
        Long invalidApplicantId = -1L;

        ResponseEntity<Object> response = applicantService.getApplicantById(invalidApplicantId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(applicantRepository, times(1)).findById(invalidApplicantId);
    }



    private ApplicantModel getSampleApplicantModel() {
        return new ApplicantModel(
                null,
                "John",
                "Doe",
                "Male",
                new Date(),
                "john.doe@example.com",
                "1234567890",
                "Computer Science",
                "ABC University",
                3.8,
                2021,
                "123, Main Street"
        );
    }

    private ApplicantEntity getSampleApplicantEntity() {
        return new ApplicantEntity(
                null,
                "John",
                "Doe",
                "Male",
                new Date(),
                "john.doe@example.com",
                "1234567890",
                "Computer Science",
                "ABC University",
                3.8,
                2021,
                "123, Main Street"
        );
    }
}
