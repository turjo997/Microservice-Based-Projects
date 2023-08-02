package com.bjit.tss.service;

import com.bjit.tss.model.ApplicantModel;
import org.springframework.http.ResponseEntity;

public interface ApplicantService {

    ResponseEntity<Object> createApplicant(ApplicantModel applicantModel);

    ResponseEntity<Object> updateApplicant(Long applicantId, ApplicantModel applicantModel);

    ResponseEntity<Object> deleteApplicant(Long applicantId);

    ResponseEntity<Object> getAllApplicants();

    ResponseEntity<Object> getApplicantById(Long applicantId);
}
