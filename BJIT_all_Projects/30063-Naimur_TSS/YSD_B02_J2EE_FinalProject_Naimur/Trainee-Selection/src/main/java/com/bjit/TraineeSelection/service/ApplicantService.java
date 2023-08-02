package com.bjit.TraineeSelection.service;

import com.bjit.TraineeSelection.entity.Application;
import com.bjit.TraineeSelection.entity.Circular;
import com.bjit.TraineeSelection.exception.ApplicationAlreadyExistsException;
import com.bjit.TraineeSelection.model.ApplicantDto;
import com.bjit.TraineeSelection.model.ApplicationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ApplicantService {
    static void findById(Long applicantId) {
    }

    public ResponseEntity<Object>applyCircular(ApplicationDto applicationDto) throws ApplicationAlreadyExistsException;


    ResponseEntity<Object> viewCircular(Long circularId);

    ResponseEntity<List<Circular>> getAllCirculars();

    ResponseEntity<Object> getApplicantId(String email);

    List<Application> ApplicantApplications(Long applicantId);
    ResponseEntity<byte[]> getAdmitCard(Long applicantId);

    void uploadImageAndCv(Long applicantId,MultipartFile image, MultipartFile cv) throws IOException;
}
