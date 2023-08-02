package com.bjit.tss.service.impl;

import com.bjit.tss.entity.CandidateMarks;
import com.bjit.tss.entity.ExamineeInfo;
import com.bjit.tss.entity.LoginInfo;
import com.bjit.tss.exception.UserException;
import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.response.ApplicantDashboardMessage;
import com.bjit.tss.model.response.ListResponse;
import com.bjit.tss.repository.CandidateRepository;
import com.bjit.tss.repository.ExamineeRepository;
import com.bjit.tss.enums.Role;
import com.bjit.tss.service.ApplicantDashboardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicantDashboardServiceImpl implements ApplicantDashboardService {

    private final ExamineeRepository examineeRepository;
    private final CandidateRepository candidateRepository;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> getApplicantDashboardData() {

        LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ExamineeInfo> examineeInfo = examineeRepository.findAllByUserInfoUserIdAndCourseInfoIsAvailable(loginInfo.getUserInfo().getUserId(), true);
        List<ExamineeInfo> filteredApplicant = examineeInfo.stream().filter(examinee -> {
            if (examinee.getRole() == Role.APPLICANT) {
                return true;
            } else {
                return false;
            }
        }).toList();

        List<CandidateMarks> candidateMarks = candidateRepository.findAllByExamineeInfoUserInfoUserIdAndExamineeInfoCourseInfoIsAvailableAndExamineeInfoRole(loginInfo.getUserInfo().getUserId(), true, Role.CANDIDATE);
        List<ApplicantDashboardMessage> dashboardMessage;
        List<ApplicantDashboardMessage> dashboardMessageFiltered;
        dashboardMessageFiltered = filteredApplicant.stream().map(examinee -> {

            ApplicantDashboardMessage applicantDashboardMessage = new ApplicantDashboardMessage();
            applicantDashboardMessage.setDashboardMessage(examinee.getCourseInfo().getApplicantDashboardMessage());
            applicantDashboardMessage.setCourseName(examinee.getCourseInfo().getCourseName());
            applicantDashboardMessage.setAdmitCardDownload(false);
            applicantDashboardMessage.setExamineeId(examinee.getExamineeId());

            return applicantDashboardMessage;
        }).toList();

        dashboardMessage = candidateMarks.stream().map(candidate -> {
            ApplicantDashboardMessage applicantDashboardMessage = new ApplicantDashboardMessage();
            if (candidate.getExamineeInfo().getRole() == Role.CANDIDATE) {

                if (candidate.getHrViva().getPassed() != null) {
                    if (candidate.getHrViva().getPassed()) {
                        applicantDashboardMessage.setDashboardMessage(candidate.getExamineeInfo().getCourseInfo().getHrVivaPassedDashboardMessage());
                        applicantDashboardMessage.setAdmitCardDownload(false);

                    } else {
                        applicantDashboardMessage.setDashboardMessage("Sorry you did not qualify HR viva. Best of luck.");
                        applicantDashboardMessage.setAdmitCardDownload(false);
                    }

                } else if (candidate.getTechnicalViva().getPassed() != null) {

                    if (candidate.getTechnicalViva().getPassed()) {
                        applicantDashboardMessage.setDashboardMessage(candidate.getExamineeInfo().getCourseInfo().getTechnicalVivaPassedDashboardMessage());
                        applicantDashboardMessage.setAdmitCardDownload(false);

                    } else {
                        applicantDashboardMessage.setDashboardMessage("Sorry you did not qualify technical viva. Best of luck.");
                        applicantDashboardMessage.setAdmitCardDownload(false);
                    }

                } else if (candidate.getAptitudeTest().getPassed() != null) {

                    if (candidate.getAptitudeTest().getPassed()) {
                        applicantDashboardMessage.setDashboardMessage(candidate.getExamineeInfo().getCourseInfo().getAptitudeTestPassedDashboardMessage());
                        applicantDashboardMessage.setAdmitCardDownload(false);

                    } else {
                        applicantDashboardMessage.setDashboardMessage("Sorry you did not qualify aptitude test. Best of luck.");
                        applicantDashboardMessage.setAdmitCardDownload(false);
                    }

                } else if (candidate.getWrittenMarks().getPassed() != null) {

                    if (candidate.getWrittenMarks().getPassed()) {
                        applicantDashboardMessage.setDashboardMessage(candidate.getExamineeInfo().getCourseInfo().getWrittenPassedDashboardMessage());
                        applicantDashboardMessage.setAdmitCardDownload(false);

                    } else {
                        applicantDashboardMessage.setDashboardMessage("Sorry you did not qualify written test. Best of luck.");
                        applicantDashboardMessage.setAdmitCardDownload(false);
                    }
                } else {

                    applicantDashboardMessage.setDashboardMessage(candidate.getExamineeInfo().getCourseInfo().getWrittenShortlistedDashboardMessage());
                    applicantDashboardMessage.setAdmitCardDownload(true);
                }

            } else if (candidate.getExamineeInfo().getRole() == Role.TRAINEE) {
                applicantDashboardMessage.setDashboardMessage(candidate.getExamineeInfo().getCourseInfo().getTraineeDashboardMessage());
                applicantDashboardMessage.setAdmitCardDownload(false);

            } else {
                throw new UserException("Invalid Request");
            }
            applicantDashboardMessage.setCourseName(candidate.getExamineeInfo().getCourseInfo().getCourseName());
            applicantDashboardMessage.setExamineeId(candidate.getExamineeInfo().getExamineeId());
            return applicantDashboardMessage;
        }).toList();

        ArrayList<ApplicantDashboardMessage> merge = new ArrayList<ApplicantDashboardMessage>();
        merge.addAll(dashboardMessage);
        merge.addAll(dashboardMessageFiltered);

        if (merge.size() == 0) {
            List<ApplicantDashboardMessage> applicantDashboardMessage = new ArrayList<>();

            ApplicantDashboardMessage applicantDashboardMessage1 = new ApplicantDashboardMessage();
            applicantDashboardMessage1.setDashboardMessage("You have not been applied to any course");
            applicantDashboardMessage.add(applicantDashboardMessage1);
            ListResponse<?> listResponse = ListResponse.builder()
                    .dataLength(0)
                    .listResponse(applicantDashboardMessage)
                    .build();
            return ApiResponseMapper.mapToResponseEntityOK(listResponse, "Applicant Dashboard");
        } else {
            ListResponse<?> listResponse = ListResponse.builder()
                    .dataLength(merge.size())
                    .listResponse(merge)
                    .build();
            return ApiResponseMapper.mapToResponseEntityOK(listResponse, "Applicant Dashboard");
        }
    }
}