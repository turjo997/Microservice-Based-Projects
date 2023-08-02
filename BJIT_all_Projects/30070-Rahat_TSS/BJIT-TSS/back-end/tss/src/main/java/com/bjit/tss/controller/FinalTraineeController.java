package com.bjit.tss.controller;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.model.request.FinalTraineeSelectionRequest;
import com.bjit.tss.service.FinalTraineeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/final-trainee")
public class FinalTraineeController {

    private final FinalTraineeService finalTraineeService;

    @GetMapping("/all-passed/{batchCode}")
    public ResponseEntity<ApiResponse<?>> allPassedFinalTrainee (@PathVariable String batchCode){
        return finalTraineeService.allPassedFinalTrainee(batchCode);
    }

    @PostMapping("/select")
    public ResponseEntity<ApiResponse<?>> selectFinalTrainee (@Valid @RequestBody FinalTraineeSelectionRequest request){
        return finalTraineeService.selectFinalTrainee(request);
    }

    @GetMapping("/{batchCode}")
    public ResponseEntity<ApiResponse<?>> allFinalTrainee (@PathVariable String batchCode){
        return finalTraineeService.allFinalTrainee(batchCode);
    }
}
