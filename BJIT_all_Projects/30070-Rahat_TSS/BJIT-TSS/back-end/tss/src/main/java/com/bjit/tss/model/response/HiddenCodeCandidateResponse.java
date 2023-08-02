package com.bjit.tss.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HiddenCodeCandidateResponse {

    private Long candidateId;
    private String firstName;
    private String lastName;
    private String email;
    private Float cgpa;
    private String presentAddress;
    private String degreeName;
    private Integer passingYear;
    private String courseName;
    private Long hiddenCode;
}
