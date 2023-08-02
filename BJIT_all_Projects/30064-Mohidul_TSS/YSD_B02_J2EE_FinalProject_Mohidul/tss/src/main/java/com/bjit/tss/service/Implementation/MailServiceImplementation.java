package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.AdmitcardEntity;
import com.bjit.tss.entity.MailEntity;
import com.bjit.tss.entity.WrittenTestEntity;
import com.bjit.tss.model.MailModel;
import com.bjit.tss.repository.AdmitcardRepository;
import com.bjit.tss.repository.MailRepository;
import com.bjit.tss.repository.WrittenTestRepository;
import com.bjit.tss.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

//for mail
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.internet.InternetAddress;


@Service
@RequiredArgsConstructor
public class MailServiceImplementation implements MailService {

    private final MailRepository mailRepository;
    private final AdmitcardRepository admitcardRepository;
    private final WrittenTestRepository writtenTestRepository;

    @Autowired
    private Environment environment;


    @Override
    public ResponseEntity<Object> createMail(MailModel mailModel) {
        MailEntity mailEntity = MailEntity.builder()
                .subject(mailModel.getSubject())
                .body(mailModel.getBody())
                .timestamp(new Date())
                .build();

        MailEntity savedMail = mailRepository.save(mailEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMail);
    }

    @Override
    public ResponseEntity<Object> updateMail(Long mailId, MailModel mailModel) {
        Optional<MailEntity> optionalMail = mailRepository.findById(mailId);
        if (optionalMail.isPresent()) {
            MailEntity existingMail = optionalMail.get();
            existingMail.setSubject(mailModel.getSubject());
            existingMail.setBody(mailModel.getBody());
            existingMail.setTimestamp(new Date());

            mailRepository.save(existingMail);
            return ResponseEntity.ok(existingMail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> deleteMail(Long mailId) {
        Optional<MailEntity> optionalMail = mailRepository.findById(mailId);
        if (optionalMail.isPresent()) {
            MailEntity existingMail = optionalMail.get();
            mailRepository.delete(existingMail);
            return ResponseEntity.ok(existingMail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getAllMails() {
        List<MailEntity> mails = mailRepository.findAll();
        return ResponseEntity.ok(mails);
    }

    @Override
    public ResponseEntity<Object> getMailById(Long mailId) {
        Optional<MailEntity> optionalMail = mailRepository.findById(mailId);
        if (optionalMail.isPresent()) {
            MailEntity mail = optionalMail.get();
            return ResponseEntity.ok(mail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> sendMailToApplicants2(Long mailId, String username, String password) {

        Optional<MailEntity> optionalMail = mailRepository.findById(mailId);

        if (optionalMail.isPresent()) {
            MailEntity mail = optionalMail.get();

            List<AdmitcardEntity> admitcards = admitcardRepository.findAll();

            String subject = mail.getSubject();
            String body = mail.getBody();

//            String username = "mohidul2300@gmail.com";
//            String password = "oeumpprcddiqanou";

            for (AdmitcardEntity admitcard : admitcards) {

                String recipient = admitcard.getCandidateId().getApplicant().getEmail();

                // using JavaMail:
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.host", environment.getProperty("mail.smtp.host"));
                    props.put("mail.smtp.port", environment.getProperty("mail.smtp.port"));
                    props.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
                    props.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));

                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });


                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                    message.setSubject(subject);
                    message.setText(body);

                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while sending emails.");
                }

            }

            return ResponseEntity.ok("Emails sent to applicants.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> sendMailToInterviewCandidate(Long mailId, String username, String password) {
        Optional<MailEntity> optionalMail = mailRepository.findById(mailId);

        if (optionalMail.isPresent()) {
            MailEntity mail = optionalMail.get();

            // Filter admitcards with marks greater than 0
            List<AdmitcardEntity> admitcards = admitcardRepository.findAll().stream()
                    .filter(admitcard -> {
                        Optional<WrittenTestEntity> writtenTest = writtenTestRepository.findByApplicantId(admitcard.getCandidateId().getApplicant().getApplicantId());
                        return writtenTest.isPresent() && writtenTest.get().getMark() > 0;
                    })
                    .collect(Collectors.toList());

            String subject = mail.getSubject();
            String body = mail.getBody();

//            String username = "mohidul2300@gmail.com";
//            String password = "oeumpprcddiqanou";

            for (AdmitcardEntity admitcard : admitcards) {

                String recipient = admitcard.getCandidateId().getApplicant().getEmail();

                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.host", environment.getProperty("mail.smtp.host"));
                    props.put("mail.smtp.port", environment.getProperty("mail.smtp.port"));
                    props.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
                    props.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));

                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });


                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                    message.setSubject(subject);
                    message.setText(body);

                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while sending emails.");
                }

            }

            return ResponseEntity.ok("Emails sent to applicants.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
