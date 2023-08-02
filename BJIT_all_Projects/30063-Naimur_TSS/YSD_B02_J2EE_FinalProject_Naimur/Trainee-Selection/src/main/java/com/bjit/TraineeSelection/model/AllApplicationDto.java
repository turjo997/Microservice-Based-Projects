package com.bjit.TraineeSelection.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllApplicationDto {
        private Long applicationId;
        private boolean approvalStatus;
        private Long circularId;
        private String circularName;
        private String circularDetail;
        private Long applicantId;
        private String firstName;
        private String lastName;
        private String gender;
        private String dob;
        private String email;
        private String contactNumber;
        private String degreeName;
        private String educationalInstitute;
        private String cgpa;
        private String passingYear;
        private String address;
    }


