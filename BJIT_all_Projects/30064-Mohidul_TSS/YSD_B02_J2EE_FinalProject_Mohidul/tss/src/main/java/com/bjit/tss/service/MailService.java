package com.bjit.tss.service;

import com.bjit.tss.model.MailModel;
import org.springframework.http.ResponseEntity;

public interface MailService {

    ResponseEntity<Object> createMail(MailModel mailModel);

    ResponseEntity<Object> updateMail(Long mailId, MailModel mailModel);

    ResponseEntity<Object> deleteMail(Long mailId);

    ResponseEntity<Object> getAllMails();

    ResponseEntity<Object> getMailById(Long mailId);

    ResponseEntity<Object> sendMailToApplicants2(Long mailId, String username, String password);


    ResponseEntity<Object> sendMailToInterviewCandidate(Long mailId, String username, String password);
}

