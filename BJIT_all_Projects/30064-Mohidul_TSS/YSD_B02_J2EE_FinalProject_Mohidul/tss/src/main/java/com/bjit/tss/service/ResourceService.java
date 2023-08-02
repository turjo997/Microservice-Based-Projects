package com.bjit.tss.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {
    ResponseEntity<Object> saveResourceByApplicantId(Long applicantId, MultipartFile photo, MultipartFile cv);

    ResponseEntity<Object> getResourceByApplicantId(Long applicantId);

    ResponseEntity<Object> deleteResourceByApplicantId(Long applicantId);
}
