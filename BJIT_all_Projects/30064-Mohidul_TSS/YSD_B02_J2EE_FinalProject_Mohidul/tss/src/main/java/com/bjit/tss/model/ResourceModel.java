package com.bjit.tss.model;

import com.bjit.tss.entity.ApplicantEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceModel {
    private Long rsId;
    private ApplicantEntity applicant;
    private byte[] photoData;
    private byte[] cvData;
}
