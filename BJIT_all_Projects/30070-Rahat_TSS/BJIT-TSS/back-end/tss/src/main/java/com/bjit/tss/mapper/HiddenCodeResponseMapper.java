package com.bjit.tss.mapper;

import com.bjit.tss.entity.HiddenCodeInfo;
import com.bjit.tss.model.response.HiddenCodeCandidateResponse;

public class HiddenCodeResponseMapper {

    public static HiddenCodeCandidateResponse mapToHiddenCodeResponse(HiddenCodeInfo hiddenCodeInfo) {
        return HiddenCodeCandidateResponse.builder()
                .candidateId(hiddenCodeInfo.getCandidateMarks().getCandidateId())
                .firstName(hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getUserInfo().getFirstName())
                .lastName(hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getUserInfo().getLastName())
                .email(hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getUserInfo().getEmail())
                .cgpa(hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getUserInfo().getCgpa())
                .presentAddress(hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getUserInfo().getPresentAddress())
                .degreeName(hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getUserInfo().getDegreeName())
                .passingYear(hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getUserInfo().getPassingYear())
                .courseName(hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getCourseInfo().getCourseName())
                .hiddenCode(hiddenCodeInfo.getHiddenCode())
                .build();
    }
}