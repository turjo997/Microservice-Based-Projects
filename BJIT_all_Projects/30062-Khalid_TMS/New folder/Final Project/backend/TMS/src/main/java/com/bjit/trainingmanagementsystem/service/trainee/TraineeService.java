package com.bjit.trainingmanagementsystem.service.trainee;


import com.bjit.trainingmanagementsystem.entities.roleEntites.TraineeEntity;
import com.bjit.trainingmanagementsystem.models.Roles.TraineeModel;
import com.bjit.trainingmanagementsystem.models.Roles.TraineeUpdateModel;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchAssignmentRequest;
import org.springframework.core.io.Resource;

import java.util.List;

public interface TraineeService {
    List<TraineeModel> getTrainees();

    List<TraineeModel> getTraineesByBatchId(Long batchId);
    List<TraineeModel>  getUnassignedTrainees();
    TraineeModel assignBatch(BatchAssignmentRequest batchAssignmentRequest);

    TraineeModel findTraineeById(Long traineeId);

    TraineeUpdateModel updateTrainee(TraineeUpdateModel traineeUpdateModel, Long traineeId);


    TraineeModel findTraineeByUserId(Long userId);

    Resource getProfilePicture(Long traineeId);
}
