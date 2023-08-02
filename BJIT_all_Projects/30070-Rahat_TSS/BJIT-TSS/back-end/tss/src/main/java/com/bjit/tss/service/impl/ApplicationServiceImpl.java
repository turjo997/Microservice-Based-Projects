package com.bjit.tss.service.impl;

import com.bjit.tss.entity.*;
import com.bjit.tss.exception.CourseException;
import com.bjit.tss.exception.ExamineeException;
import com.bjit.tss.exception.UserException;
import com.bjit.tss.mapper.ApiResponseMapper;
import com.bjit.tss.mapper.CandidateMapper;
import com.bjit.tss.model.request.SpecificInstitutionExamineeRequest;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.ApplicationRequest;
import com.bjit.tss.model.request.CourseRoleRequest;
import com.bjit.tss.model.response.ListResponse;
import com.bjit.tss.model.response.CandidateResponse;
import com.bjit.tss.repository.CandidateRepository;
import com.bjit.tss.repository.CourseRepository;
import com.bjit.tss.repository.ExamineeRepository;
import com.bjit.tss.repository.UserRepository;
import com.bjit.tss.enums.Role;
import com.bjit.tss.service.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ExamineeRepository examineeRepository;
    private final CandidateRepository candidateRepository;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> applyCourse(ApplicationRequest applicationRequest) {
        LoginInfo loginInfo = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<CourseInfo> courseInfo = courseRepository.findByBatchCode(applicationRequest.getBatchCode());
        if (courseInfo.isEmpty() || !courseInfo.get().getIsAvailable()) {
            throw new CourseException("Invalid Batch Code : " + applicationRequest.getBatchCode());
        }

        Optional<ExamineeInfo> examinee = examineeRepository.findByUserInfoUserIdAndCourseInfoCourseId(loginInfo.getUserInfo().getUserId(), courseInfo.get().getCourseId());
        if (examinee.isPresent()) {
            throw new ExamineeException("You are already registered for this course");
        }

        Optional<UserInfo> userInfo = userRepository.findById(loginInfo.getUserInfo().getUserId());
        if (userInfo.isEmpty()) {
            throw new UserException("No User Found");
        }

        ExamineeInfo examineeInfo = ExamineeInfo.builder()
                .userInfo(userInfo.get())
                .courseInfo(courseInfo.get())
                .applicationTime(new Date(System.currentTimeMillis()))
                .role(Role.APPLICANT)
                .build();
        ExamineeInfo savedApplication = examineeRepository.save(examineeInfo);
        return ApiResponseMapper.mapToResponseEntityCreated(savedApplication, "Application was successful");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> allApplicationSpecific(CourseRoleRequest courseRoleRequest) {
        Optional<List<ExamineeInfo>> examineeInfos = examineeRepository.findByRoleAndCourseInfoIsAvailableAndCourseInfoBatchCode(courseRoleRequest.getRole(), true, courseRoleRequest.getBatchCode());
        if (examineeInfos.isEmpty()) {
            throw new UserException("User not found");
        }

        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(examineeInfos.get().size())
                .listResponse(examineeInfos)
                .build();
        return ApiResponseMapper.mapToResponseEntityOK(listResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> allUnassignedCandidates(CourseRoleRequest courseRoleRequest) {

        List<CandidateMarks> candidateMarksList = candidateRepository.findAllByExamineeInfoRoleAndExamineeInfoCourseInfoBatchCodeAndWrittenMarksEvaluatorInfoIsNull(courseRoleRequest.getRole(), courseRoleRequest.getBatchCode());
        List<CandidateResponse> candidateResponseList = candidateMarksList.stream().map(CandidateMapper::mapToCandidateResponse).toList();

        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(candidateResponseList.size())
                .listResponse(candidateResponseList)
                .build();

        return ApiResponseMapper.mapToResponseEntityOK(listResponse, "All the " + courseRoleRequest.getRole() + " of the batch " + courseRoleRequest.getBatchCode() + " who have not been assigned to any evaluator for written mark.");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> allExamineSpecificInstitution( SpecificInstitutionExamineeRequest specificInstitutionExamineeRequest) {
        List<ExamineeInfo> examineeInfos = examineeRepository.findDistinctByUserInfoEducationalInstituteAndRoleAndCourseInfoIsAvailableAndCourseInfoBatchCode(specificInstitutionExamineeRequest.getEducationalInstitution(), specificInstitutionExamineeRequest.getRole(), true, specificInstitutionExamineeRequest.getBatchCode());
        if (examineeInfos.size()==0) {
            throw new UserException("No user found for this institution : "+specificInstitutionExamineeRequest.getEducationalInstitution());
        }

        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(examineeInfos.size())
                .listResponse(examineeInfos)
                .build();

        return ApiResponseMapper.mapToResponseEntityOK(listResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> allDistinctInstitution(CourseRoleRequest courseRoleRequest) {
        List<String> institute = examineeRepository.findDistinctEducationalInstitutesByRoleAndCourseInfoIsAvailableAndCourseInfoBatchCode( courseRoleRequest.getRole(), true, courseRoleRequest.getBatchCode());
        ListResponse<?> listResponse = ListResponse.builder()
                .dataLength(institute.size())
                .listResponse(institute)
                .build();

        return ApiResponseMapper.mapToResponseEntityOK(listResponse);
    }
}