package com.bjit.trainingmanagementsystem.service.trainer;

import com.bjit.trainingmanagementsystem.entities.BatchEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TraineeEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import com.bjit.trainingmanagementsystem.models.Roles.TraineeModel;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerUpdateModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchAssignmentRequest;
import org.springframework.core.io.Resource;

import java.util.List;

public interface TrainerService {
    List<TrainerModel> getTrainers();

    List<TrainerModel> getUnassignedTrainers(Long batchId);

    List<BatchEntity> getBatchList(Long trainerId);

    TrainerModel findTrainerById(Long trainerId);

    TrainerUpdateModel updateTrainer(TrainerUpdateModel trainerUpdateModel, Long trainerId);


    TrainerModel findTrainerByUserId(Long userId);

    Resource getProfilePicture(Long trainerId);
}
