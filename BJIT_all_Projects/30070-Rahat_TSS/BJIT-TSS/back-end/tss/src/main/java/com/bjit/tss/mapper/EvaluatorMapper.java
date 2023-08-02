package com.bjit.tss.mapper;

import com.bjit.tss.entity.HiddenCodeInfo;
import com.bjit.tss.model.response.EvaluatorAssignmentResponse;

public class EvaluatorMapper {

    public static EvaluatorAssignmentResponse mapToEvaluatorAssignmentResponse(HiddenCodeInfo hiddenCodeInfo) {


        return EvaluatorAssignmentResponse.builder()
                .candidateEmail(hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getUserInfo().getEmail())
                .candidateName(hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getUserInfo().getFirstName() + " " + hiddenCodeInfo.getCandidateMarks().getExamineeInfo().getUserInfo().getLastName())
                .evaluatorEmail(hiddenCodeInfo.getCandidateMarks().getWrittenMarks().getEvaluatorInfo().getEmail())
                .evaluatorName(hiddenCodeInfo.getCandidateMarks().getWrittenMarks().getEvaluatorInfo().getName())
                .build();

    }
}
