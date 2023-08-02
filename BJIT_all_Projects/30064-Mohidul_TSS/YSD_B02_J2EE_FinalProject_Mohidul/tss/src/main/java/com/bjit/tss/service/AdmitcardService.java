package com.bjit.tss.service;

import com.bjit.tss.model.AdmitcardModel;
import org.springframework.http.ResponseEntity;

public interface AdmitcardService {
    ResponseEntity<Object> createAdmitcard(AdmitcardModel admitcardModel);
    ResponseEntity<Object> updateAdmitcard(Long admitcardId, AdmitcardModel admitcardModel);
    ResponseEntity<Object> deleteAdmitcard(Long admitcardId);
    ResponseEntity<Object> getAllAdmitcards();
    ResponseEntity<Object> getAdmitcardById(Long applicantId);
    ResponseEntity<Object> generateAdmitcardPdf(Long applicantId);
}
