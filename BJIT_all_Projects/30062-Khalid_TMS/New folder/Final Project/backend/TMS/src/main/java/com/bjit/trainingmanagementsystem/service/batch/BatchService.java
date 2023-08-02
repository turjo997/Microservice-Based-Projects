package com.bjit.trainingmanagementsystem.service.batch;

import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchAssignmentRequest;
import com.bjit.trainingmanagementsystem.models.batch.BatchModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchUpdateModel;

import java.util.List;

public interface BatchService {
    BatchModel create(BatchModel batchModel);
    List<BatchModel> getAllBatches();
    List<TrainerModel> getTrainerListByBatch(Long batchId);
    BatchModel getBatchById(Long batchId);
    BatchModel updateBatch(BatchUpdateModel batchUpdateModel, Long batchId);
    BatchModel assignTrainer(BatchAssignmentRequest batchAssignmentRequest);


}
