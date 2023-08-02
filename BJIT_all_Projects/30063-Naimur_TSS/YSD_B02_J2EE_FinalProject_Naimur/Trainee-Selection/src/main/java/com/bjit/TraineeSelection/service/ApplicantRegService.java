package com.bjit.TraineeSelection.service;

import com.bjit.TraineeSelection.entity.UserEntity;
import com.bjit.TraineeSelection.model.ApplicantDto;
import org.springframework.http.ResponseEntity;

public interface ApplicantRegService {
    ResponseEntity<Object> register(ApplicantDto applicantDto);
    void AdminRegister();
}
