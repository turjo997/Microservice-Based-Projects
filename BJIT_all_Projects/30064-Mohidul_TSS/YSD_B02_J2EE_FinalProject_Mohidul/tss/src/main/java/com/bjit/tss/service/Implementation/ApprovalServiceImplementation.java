package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.ApprovalEntity;
import com.bjit.tss.model.ApprovalModel;
import com.bjit.tss.repository.ApprovalRepository;
import com.bjit.tss.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImplementation implements ApprovalService {

    private final ApprovalRepository approvalRepository;

    @Override
    public ResponseEntity<Object> createApproval(ApprovalModel approvalModel) {
        Optional<ApprovalEntity> existingApproval = approvalRepository.findByApplicantAndCircular(
                approvalModel.getApplicant(),
                approvalModel.getCircular()
        );

        if (existingApproval.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Applicant has already applied for this circular");
        }

        ApprovalEntity approvalEntity = new ApprovalEntity();
        approvalEntity.setApplicant(approvalModel.getApplicant());
        approvalEntity.setCircular(approvalModel.getCircular());
        approvalEntity.setApproved(false); // by default it is false

        ApprovalEntity savedApproval = approvalRepository.save(approvalEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApproval);
    }


    @Override
    public ResponseEntity<Object> updateApproval(Long approvalId, ApprovalModel approvalModel) {
        Optional<ApprovalEntity> optionalApproval = approvalRepository.findById(approvalId);
        Optional<ApprovalEntity> checkApproval = approvalRepository.findByApplicant_ApplicantIdAndCircular_CircularId(approvalModel.getApplicant().getApplicantId(), approvalModel.getCircular().getCircularId());
        if (optionalApproval.isPresent() && checkApproval.isPresent()) {
            ApprovalEntity existingApproval = optionalApproval.get();

            List<ApprovalEntity> existingApprovals = approvalRepository.findByApplicant_ApplicantIdAndCircularNot(approvalModel.getApplicant().getApplicantId(), approvalModel.getCircular());
            boolean isPreviousApprovalFound = existingApprovals.stream()
                    .anyMatch(approval -> approval.isApproved());

            if (isPreviousApprovalFound) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Applicant is already approved for another circular.");
            }

            existingApproval.setApproved(approvalModel.isApproved());

            approvalRepository.save(existingApproval);
            return ResponseEntity.ok(existingApproval);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<Object> deleteApproval(Long approvalId) {
        Optional<ApprovalEntity> optionalApproval = approvalRepository.findById(approvalId);
        if(optionalApproval.isPresent()) {
            ApprovalEntity existingApproval = optionalApproval.get();
            approvalRepository.delete(existingApproval);
            return ResponseEntity.ok(existingApproval);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Object> getAllApprovals() {
        List<ApprovalEntity> approvals = approvalRepository.findAll();
        return ResponseEntity.ok(approvals);
    }

    @Override
    public ResponseEntity<Object> getApprovalById(Long approvalId) {
        Optional<ApprovalEntity> optionalApproval = approvalRepository.findById(approvalId);
        if(optionalApproval.isPresent()) {
            ApprovalEntity approval = optionalApproval.get();
            return ResponseEntity.ok(approval);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
