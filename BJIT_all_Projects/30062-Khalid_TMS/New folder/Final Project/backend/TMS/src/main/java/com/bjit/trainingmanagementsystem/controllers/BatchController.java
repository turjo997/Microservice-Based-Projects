package com.bjit.trainingmanagementsystem.controllers;

import com.bjit.trainingmanagementsystem.entities.BatchEntity;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchAssignmentRequest;
import com.bjit.trainingmanagementsystem.models.batch.BatchModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchUpdateModel;
import com.bjit.trainingmanagementsystem.service.batch.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class  BatchController {

    private final BatchService batchService;

    @PostMapping("/create")
    public ResponseEntity<BatchModel> createBatch(@RequestBody BatchModel batchModel){
        return new ResponseEntity<>(batchService.create(batchModel), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BatchModel>> getAllBatches() {
        return new ResponseEntity<>(batchService.getAllBatches(), HttpStatus.OK);
    }

    @GetMapping("/trainers/{batchId}")
    public ResponseEntity<List<TrainerModel>> getTrainersByBatchId(@PathVariable Long batchId) {
        return new ResponseEntity<>(batchService.getTrainerListByBatch(batchId), HttpStatus.OK);
    }

    @PutMapping("/update/{batchId}")
    public ResponseEntity<BatchModel> updateBatch(@PathVariable Long batchId, @RequestBody BatchUpdateModel batchUpdateModel) {
        return new ResponseEntity<>(batchService.updateBatch(batchUpdateModel, batchId), HttpStatus.OK);
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<BatchModel> getBatchById(@PathVariable Long batchId) {
        return new ResponseEntity<>(batchService.getBatchById(batchId), HttpStatus.OK);
    }

    @PostMapping("/trainer-assign")
    public ResponseEntity<BatchModel> assignBatch(@RequestBody BatchAssignmentRequest batchAssignmentRequest) {
        return new ResponseEntity<>(batchService.assignTrainer(batchAssignmentRequest), HttpStatus.OK);
    }
}
