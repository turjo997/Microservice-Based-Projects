package com.bjit.trainingmanagementsystem.controllers;

import com.bjit.trainingmanagementsystem.models.Roles.TraineeModel;
import com.bjit.trainingmanagementsystem.models.Roles.TraineeUpdateModel;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerUpdateModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchAssignmentRequest;
import com.bjit.trainingmanagementsystem.service.trainer.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping
    public ResponseEntity<List<TrainerModel>> getTrainers() {
        return new ResponseEntity<>(trainerService.getTrainers(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<TrainerModel> findTrainerByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(trainerService.findTrainerByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/unassigned/{batchId}")
    public ResponseEntity<List<TrainerModel>> getUnassignedTrainers(@PathVariable Long batchId) {
        return new ResponseEntity<>(trainerService.getUnassignedTrainers(batchId), HttpStatus.OK);
    }

    @GetMapping("/{trainerId}")
    public ResponseEntity<TrainerModel> findTrainerById(@PathVariable Long trainerId) {
        return new ResponseEntity<>(trainerService.findTrainerById(trainerId), HttpStatus.OK);
    }

    @PutMapping("/update/{trainerId}")
    public ResponseEntity<TrainerUpdateModel> updateTrainer(@PathVariable Long trainerId, @RequestBody TrainerUpdateModel trainerUpdateModel) {
        return new ResponseEntity<>(trainerService.updateTrainer(trainerUpdateModel, trainerId), HttpStatus.OK);
    }

    @GetMapping("/batch-list/{trainerId}")
    public ResponseEntity<Object> getBatchList(@PathVariable Long trainerId) {
        return new ResponseEntity<>(trainerService.getBatchList(trainerId), HttpStatus.OK);
    }

    @GetMapping("/profile-picture/{trainerId}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable Long trainerId) {
        Resource resource = trainerService.getProfilePicture(trainerId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

}
