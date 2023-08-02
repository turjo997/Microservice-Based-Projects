package com.bjit.tss.model;

import com.bjit.tss.entity.ApplicantEntity;
import com.bjit.tss.entity.CircularEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalModel {
    private Long approvalId;
    private ApplicantEntity applicant;
    private CircularEntity circular;
    private boolean isApproved;
}
