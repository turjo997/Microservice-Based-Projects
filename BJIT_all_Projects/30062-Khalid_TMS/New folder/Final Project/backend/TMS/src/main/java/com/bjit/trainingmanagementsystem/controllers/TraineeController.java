package com.bjit.trainingmanagementsystem.controllers;

import com.bjit.trainingmanagementsystem.models.Roles.TraineeModel;
import com.bjit.trainingmanagementsystem.models.Roles.TraineeUpdateModel;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchAssignmentRequest;
import com.bjit.trainingmanagementsystem.models.batch.BatchModel;
import com.bjit.trainingmanagementsystem.service.trainee.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainee")
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;

    @GetMapping
    public ResponseEntity<List<TraineeModel>> getTrainees() {
        return new ResponseEntity<>(traineeService.getTrainees(), HttpStatus.OK);
    }

    @GetMapping("/trainee-list/{batchId}")
    public ResponseEntity<List<TraineeModel>> getTraineesByBatchId(@PathVariable Long batchId) {
        return new ResponseEntity<>(traineeService.getTraineesByBatchId(batchId), HttpStatus.OK);
    }

    @GetMapping("/unassigned")
    public ResponseEntity<List<TraineeModel>> getUnassignedTrainees() {
        return new ResponseEntity<>(traineeService.getUnassignedTrainees(), HttpStatus.OK);
    }

    @GetMapping("/{traineeId}")
    public ResponseEntity<TraineeModel> findTraineeById(@PathVariable Long traineeId) {
        return new ResponseEntity<>(traineeService.findTraineeById(traineeId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<TraineeModel> findTraineeByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(traineeService.findTraineeByUserId(userId), HttpStatus.OK);
    }

    @PutMapping("/update/{traineeId}")
    public ResponseEntity<TraineeUpdateModel> updateTrainee(@PathVariable Long traineeId, @RequestBody TraineeUpdateModel traineeUpdateModel) {
        return new ResponseEntity<>(traineeService.updateTrainee(traineeUpdateModel, traineeId), HttpStatus.OK);
    }

    @PostMapping("/batch-assign")
    public ResponseEntity<Object> assignBatch(@RequestBody BatchAssignmentRequest batchAssignmentRequest) {
        return new ResponseEntity<>(traineeService.assignBatch(batchAssignmentRequest), HttpStatus.OK);
    }

    @GetMapping("/profile-picture/{traineeId}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable Long traineeId) {
        Resource resource = traineeService.getProfilePicture(traineeId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

}
