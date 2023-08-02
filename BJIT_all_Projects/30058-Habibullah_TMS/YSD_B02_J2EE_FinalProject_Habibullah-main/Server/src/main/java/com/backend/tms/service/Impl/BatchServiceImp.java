package com.backend.tms.service.Impl;

import com.backend.tms.entity.BatchEntity;
import com.backend.tms.entity.ClassroomEntity;
import com.backend.tms.exception.custom.BatchAlreadyExistsException;
import com.backend.tms.exception.custom.BatchNotFoundException;
import com.backend.tms.exception.custom.IllegalArgumentException;
import com.backend.tms.model.Batch.BatchReqModel;
import com.backend.tms.model.Batch.BatchResModel;
import com.backend.tms.repository.BatchRepository;
import com.backend.tms.repository.ClassroomRepository;
import com.backend.tms.service.BatchService;
import com.backend.tms.utlis.ValidationUtlis;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BatchServiceImp implements BatchService {
    private final BatchRepository batchRepository;
    private final ModelMapper modelMapper;
    private final ClassroomRepository classroomRepository;
    @Override
    public ResponseEntity<Object> createBatch(BatchReqModel batchModel) {
        String batchName = batchModel.getBatchName();
        //matching the pattern of the batch name
        String pattern = "^YSD_B\\d{2}_\\w+$";
        if (!batchName.matches(pattern)) {
           throw new IllegalArgumentException("Batch Name is not following the format");
        }

        //find if the batch exist of not.
        BatchEntity existingBatch = batchRepository.findByBatchName(batchName);
        if (existingBatch != null) {
            throw new BatchAlreadyExistsException("Batch already exist with the same Name!");
        }

        //duration of a batch validation
        if(!ValidationUtlis.isDateRangeValid(batchModel.getStartDate(), batchModel.getEndDate())){
            return new ResponseEntity<>("Ending Date can't same or less than Starting Date", HttpStatus.BAD_REQUEST);
        }

        if (!ValidationUtlis.isBatchDurationValid(batchModel.getStartDate(),batchModel.getEndDate())) {
            throw new IllegalArgumentException("Batch duration should be 4 months.");
        }




        BatchEntity batchEntity = modelMapper.map(batchModel,BatchEntity.class);
        // Add classroom for the batch
        ClassroomEntity classroomEntity = ClassroomEntity.builder()
                .id(batchEntity.getId())
                .className(batchEntity.getBatchName())
                .build();

        classroomRepository.save(classroomEntity);
        batchEntity.setClassroom(classroomEntity);;
        batchRepository.save(batchEntity);
        return new ResponseEntity<>("Batch added successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> getBatch(Long batchId) {
        // Check if the batch exists
        BatchEntity batchEntity = batchRepository.findById(batchId)
                .orElseThrow(() -> new BatchNotFoundException("Batch not found"));
        BatchResModel batchModel = modelMapper.map(batchEntity, BatchResModel.class);
        return new ResponseEntity<>(batchModel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllBatches() {
        List<BatchEntity> batchEntities = batchRepository.findAll();
        // Create a response object
        List<Map<String, Object>> batchesResponse = new ArrayList<>();
        for (BatchEntity batch : batchEntities) {
            Map<String, Object> batchResponse = new HashMap<>();
            batchResponse.put("Id", batch.getId());
            batchResponse.put("BatchName", batch.getBatchName());
            batchResponse.put("StartDate", batch.getStartDate());
            batchResponse.put("EndDate", batch.getEndDate());
            batchResponse.put("TraineeCount", batch.getTrainees().size());
            batchResponse.put("TrainerCount", batch.getTrainers().size());
            batchResponse.put("ScheduleProgramCount", batch.getSchedulePrograms().size());
            batchesResponse.add(batchResponse);
        }
        return new ResponseEntity<>(batchesResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllBatchName() {
        List<BatchEntity> batchEntities = batchRepository.findAll();
        // Create a response object
        List<Map<String, Object>> batches = new ArrayList<>();
        for (BatchEntity batch : batchEntities) {
            Map<String, Object>  batchData = new HashMap<>();
            batchData.put("id", batch.getId());
            batchData.put("name", batch.getBatchName());
            batches.add(batchData);
        }
        // Create the final response
        Map<String, Object> response = new HashMap<>();
        response.put("Total Batches", batches.size());
        response.put("Batches", batches);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateBatch(Long batchId, BatchReqModel batchModel) {
        BatchEntity batchEntity = batchRepository.findById(batchId)
                .orElseThrow(() -> new BatchNotFoundException("Batch not found"));

        //duration of a batch validation
        if (ValidationUtlis.isBatchDurationValid(batchModel.getStartDate(),batchModel.getEndDate())) {
            throw new IllegalArgumentException("Batch duration should be 4 months.");
        }

        // Update the batch details
        batchEntity.setBatchName(batchModel.getBatchName());
        batchEntity.setStartDate(batchModel.getStartDate());
        batchEntity.setEndDate(batchModel.getEndDate());
        // Save the updated batch entity
        batchRepository.save(batchEntity);
        return new ResponseEntity<>("Batch updated successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteBatch(Long batchId) {
        // Check if the batch exists, if exist then delete
        batchRepository.findById(batchId).orElseThrow(()->new BatchNotFoundException("Batch not found"));
        batchRepository.deleteById(batchId);
        return new ResponseEntity<>("Batch deleted successfully", HttpStatus.OK);
    }

}
