package com.bjit.tss.controller;

import com.bjit.tss.model.request.SpecificInstitutionExamineeRequest;
import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.ApplicationRequest;
import com.bjit.tss.model.request.CourseRoleRequest;
import com.bjit.tss.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<?>> applyCourse(@Valid @RequestBody ApplicationRequest applicationRequest) {
        return applicationService.applyCourse(applicationRequest);
    }

    @PostMapping("/course")
    public ResponseEntity<ApiResponse<?>> allApplicationSpecific(@Valid @RequestBody CourseRoleRequest courseRoleRequest) {
        return applicationService.allApplicationSpecific(courseRoleRequest);
    }

    @PostMapping("/course/unassigned-candidates")
    public ResponseEntity<ApiResponse<?>> allUnassignedCandidates(@Valid @RequestBody CourseRoleRequest courseRoleRequest) {
        return applicationService.allUnassignedCandidates(courseRoleRequest);
    }

    @PostMapping("/course/distinct")
    public ResponseEntity<ApiResponse<?>> allExamineSpecificInstitution(@Valid @RequestBody SpecificInstitutionExamineeRequest specificInstitutionExamineeRequest) {
        return applicationService.allExamineSpecificInstitution(specificInstitutionExamineeRequest);
    }

    @PostMapping("/course/distinct_institution")
    public ResponseEntity<ApiResponse<?>> allDistinctInstitutions(@Valid @RequestBody CourseRoleRequest courseRoleRequest) {
        return applicationService.allDistinctInstitution(courseRoleRequest);
    }
}
