package com.bjit.TraineeSelection.controller;

import com.bjit.TraineeSelection.model.ApplicantDto;
import com.bjit.TraineeSelection.service.ApplicantRegService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applicant")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
public class ApplicantRegController {
    private final ApplicantRegService applicantRegService;
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody ApplicantDto applicantDto){
        return applicantRegService.register(applicantDto);
    }
}
