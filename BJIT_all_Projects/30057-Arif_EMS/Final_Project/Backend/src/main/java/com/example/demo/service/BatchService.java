package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor

public class BatchService {

    private final BatchRepository batchRepo;
    private final TraineeRepository traineeRepo;
    private final TrainerRepository trainerRepo;
    private final UserRepository userRepo;

    public ResponseEntity<Batch> createBatch(BatchCreate batch) {

        if (batchRepo.findByBatchName(batch.getBatchName()) != null) {
            throw new RuntimeException("Batch name not available.");
        }
        else {
            Set<Trainee> newTrainees = new HashSet<>();
            for (Long ids : batch.getTraineeIds()) {
                if( traineeRepo.findById(ids).isPresent()){
                    newTrainees.add(traineeRepo.findById(ids).get());
                }
            }
            Set<Trainer> newTrainers = new HashSet<>();
            for (Long ids : batch.getTrainerIds()) {
                if( trainerRepo.findById(ids).isPresent() ){
                    newTrainers.add(trainerRepo.findById(ids).get());
                }
            }
            Batch newBatch = Batch.builder()
                    .batchName(batch.getBatchName())
                    .startDate(batch.getStartDate())
                    .trainees(newTrainees)
                    .trainers(newTrainers)
                    .build();
            Batch savedBatch = batchRepo.save(newBatch);

            for( Trainee updatedTrainee : newTrainees ){
                updatedTrainee.setBatchId(savedBatch.getBatchId());
                traineeRepo.save(updatedTrainee);
            }

            for( Trainer updatedTrainer : newTrainers ){
                Set<Long> batches = new HashSet<>();
                if( updatedTrainer.getBatchIds() != null ){
                    batches = updatedTrainer.getBatchIds();
                }
                batches.add(savedBatch.getBatchId());
                updatedTrainer.setBatchIds(batches);
                trainerRepo.save(updatedTrainer);
            }

            return new ResponseEntity<>(savedBatch, HttpStatus.CREATED);
        }

    }

    public ResponseEntity<List<Batch>> viewAllBatches(Long userId) {

        List<Batch> batches = new ArrayList<>();
        User user = userRepo.findById(userId).get();

        if( user.getRole().equalsIgnoreCase("Admin") ){

            batches = batchRepo.findAll();

        }else if( user.getRole().equalsIgnoreCase("Trainer") ){

            Set<Long> batchIds = trainerRepo.findByUser(user).getBatchIds();

            for( Long id : batchIds ){
                batches.add( batchRepo.findById(id).get() );
            }

        }else{

            batches.add( batchRepo.findById(traineeRepo.findByUser(user).getBatchId()).get() );

        }

        return new ResponseEntity<>( batches , HttpStatus.OK);

    }

    public ResponseEntity<Batch> findBatchByBatchId(Long batchId) {

        return new ResponseEntity<>( batchRepo.findById(batchId).orElseThrow() , HttpStatus.OK);

    }

}
