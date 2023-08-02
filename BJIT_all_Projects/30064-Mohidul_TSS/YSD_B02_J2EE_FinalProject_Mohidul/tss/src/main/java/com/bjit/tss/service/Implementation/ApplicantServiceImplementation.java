package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.ApplicantEntity;
import com.bjit.tss.model.ApplicantModel;
import com.bjit.tss.repository.ApplicantRepository;
import com.bjit.tss.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImplementation implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    @Override
    public ResponseEntity<Object> createApplicant(ApplicantModel applicantModel) {
        ApplicantEntity applicantEntity = new ApplicantEntity();
        applicantEntity.setFirstName(applicantModel.getFirstName());
        applicantEntity.setLastName(applicantModel.getLastName());
        applicantEntity.setGender(applicantModel.getGender());
        applicantEntity.setDob(applicantModel.getDob());
        applicantEntity.setEmail(applicantModel.getEmail());
        applicantEntity.setContactNumber(applicantModel.getContactNumber());
        applicantEntity.setDegreeName(applicantModel.getDegreeName());
        applicantEntity.setEducationalInstitute(applicantModel.getEducationalInstitute());
        applicantEntity.setCgpa(applicantModel.getCgpa());
        applicantEntity.setPassingYear(applicantModel.getPassingYear());
        applicantEntity.setPresentAddress(applicantModel.getPresentAddress());

        ApplicantEntity savedApplicant = applicantRepository.save(applicantEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplicant);
    }

    @Override
    public ResponseEntity<Object> updateApplicant(Long applicantId, ApplicantModel applicantModel) {
        Optional<ApplicantEntity> optionalApplicant = applicantRepository.findById(applicantId);
        if (optionalApplicant.isPresent()) {
            ApplicantEntity existingApplicant = optionalApplicant.get();
            existingApplicant.setFirstName(applicantModel.getFirstName());
            existingApplicant.setLastName(applicantModel.getLastName());
            existingApplicant.setGender(applicantModel.getGender());
            existingApplicant.setDob(applicantModel.getDob());
            existingApplicant.setEmail(applicantModel.getEmail());
            existingApplicant.setContactNumber(applicantModel.getContactNumber());
            existingApplicant.setDegreeName(applicantModel.getDegreeName());
            existingApplicant.setEducationalInstitute(applicantModel.getEducationalInstitute());
            existingApplicant.setCgpa(applicantModel.getCgpa());
            existingApplicant.setPassingYear(applicantModel.getPassingYear());
            existingApplicant.setPresentAddress(applicantModel.getPresentAddress());


            applicantRepository.save(existingApplicant);
            return ResponseEntity.ok(existingApplicant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Override
    public ResponseEntity<Object> deleteApplicant(Long applicantId) {
        Optional<ApplicantEntity> optionalApplicant = applicantRepository.findById(applicantId);
        if (optionalApplicant.isPresent()) {
            ApplicantEntity existingApplicant = optionalApplicant.get();
            applicantRepository.delete(existingApplicant);
            return ResponseEntity.ok(existingApplicant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getAllApplicants() {
        List<ApplicantEntity> applicants = applicantRepository.findAll();
        return ResponseEntity.ok(applicants);
    }

    @Override
    public ResponseEntity<Object> getApplicantById(Long applicantId) {
        Optional<ApplicantEntity> optionalApplicant = applicantRepository.findById(applicantId);
        if (optionalApplicant.isPresent()) {
            ApplicantEntity applicant = optionalApplicant.get();
            return ResponseEntity.ok(applicant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
