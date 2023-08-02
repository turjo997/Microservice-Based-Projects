package com.bjit.traineeSelectionSystem.TSS.controller;

import com.bjit.traineeSelectionSystem.TSS.model.ResponseModel;
import com.bjit.traineeSelectionSystem.TSS.service.ApprovedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approved")
@RequiredArgsConstructor
public class ApprovedController {
    private final ApprovedService approvedService;
    @PostMapping("/create/{circularId}/{applicantId}")
    public ResponseEntity<ResponseModel<?>> createApplication(@PathVariable Long circularId , @PathVariable Long applicantId){
        return approvedService.applicantApproved(circularId , applicantId);
    }
    @GetMapping("/getAllApprovedByCircular/{circularId}")
    public ResponseEntity<ResponseModel<?>> getAllApplicationByCircular( @PathVariable Long circularId) {
        return approvedService.getAllApplicationByCircular(circularId);
    }
}
