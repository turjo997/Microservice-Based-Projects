package com.bjit.tss.service;

import com.bjit.tss.model.request.SpecificInstitutionExamineeRequest;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.ApplicationRequest;
import com.bjit.tss.model.request.CourseRoleRequest;
import org.springframework.http.ResponseEntity;

public interface ApplicationService {

    ResponseEntity<ApiResponse<?>> applyCourse(ApplicationRequest applicationRequest);

    ResponseEntity<ApiResponse<?>> allApplicationSpecific(CourseRoleRequest courseRoleRequest);

    ResponseEntity<ApiResponse<?>> allUnassignedCandidates(CourseRoleRequest courseRoleRequest);

    ResponseEntity<ApiResponse<?>> allExamineSpecificInstitution( SpecificInstitutionExamineeRequest specificInstitutionExamineeRequest);

    ResponseEntity<ApiResponse<?>> allDistinctInstitution(CourseRoleRequest courseRoleRequest);
}
