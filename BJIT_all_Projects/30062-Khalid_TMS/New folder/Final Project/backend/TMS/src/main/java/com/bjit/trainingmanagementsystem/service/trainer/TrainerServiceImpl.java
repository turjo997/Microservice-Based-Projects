package com.bjit.trainingmanagementsystem.service.trainer;

import com.bjit.trainingmanagementsystem.entities.BatchEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.AdminEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.exception.UnauthorizedException;
import com.bjit.trainingmanagementsystem.models.Roles.TraineeModel;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerUpdateModel;
import com.bjit.trainingmanagementsystem.repository.batch.BatchRepository;
import com.bjit.trainingmanagementsystem.repository.role.TrainerRepository;
import com.bjit.trainingmanagementsystem.service.batch.BatchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bjit.trainingmanagementsystem.utils.BeanUtilsHelper.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService{

    private final TrainerRepository trainerRepository;
    private final BatchRepository batchRepository;
    private final ModelMapper modelMapper;
    private final BatchService batchService;

    @Override
    public List<TrainerModel> getTrainers() {
        List<TrainerEntity> trainers = trainerRepository.findAll();
        return trainers.stream()
                .map(trainerEntity -> {
                    TrainerModel trainerModel = modelMapper.map(trainerEntity, TrainerModel.class);
                    trainerModel.setUserId(trainerEntity.getUser().getUserId());
                    return trainerModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainerModel> getUnassignedTrainers(Long batchId) {
        List<TrainerModel> allTrainers = getTrainers();
        List<TrainerModel> assignedTrainers = batchService.getTrainerListByBatch(batchId);
        List<TrainerModel> unassignedTrainers = new ArrayList<>(allTrainers);

        unassignedTrainers.removeAll(assignedTrainers);

        return unassignedTrainers;
    }


    @Override
    public List<BatchEntity> getBatchList(Long trainerId) {
        List<BatchEntity> batchList = new ArrayList<>();
        List<BatchEntity> allBatches = batchRepository.findAll();

        for (BatchEntity batch : allBatches) {
            List<TrainerEntity> trainers = batch.getTrainers();

            for (TrainerEntity trainer : trainers) {
                if (trainer.getTrainerId().equals(trainerId)) {
                    batchList.add(batch);
                    break;
                }
            }
        }

        return batchList;
    }

    @Override
    public TrainerModel findTrainerById(Long trainerId) {
        TrainerEntity trainerEntity = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new NotFoundException("Trainer not found.  ID: " + trainerId));

        TrainerModel trainerModel = modelMapper.map(trainerEntity, TrainerModel.class);
        trainerModel.setUserId(trainerEntity.getUser().getUserId());

        return trainerModel;
    }

    @Override
    public TrainerUpdateModel updateTrainer(TrainerUpdateModel trainerUpdateModel, Long trainerId) {

        UserEntity userEntity = (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long authenticatedUserId = userEntity.getUserId();

        TrainerEntity trainerEntity = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new NotFoundException("Trainer not found. ID: " + trainerId));

        Long requestUserId = trainerEntity.getUser().getUserId();

        if (!authenticatedUserId.equals(requestUserId)) {
            throw new UnauthorizedException("You are not Authorized to perform this action");
        }
        BeanUtils.copyProperties(trainerUpdateModel, trainerEntity, getNullPropertyNames(trainerUpdateModel));
        TrainerEntity updatedTrainer = trainerRepository.save(trainerEntity);

        return modelMapper.map(updatedTrainer, TrainerUpdateModel.class);
    }

    @Override
    public TrainerModel findTrainerByUserId(Long userId) {
        TrainerEntity trainerEntity = trainerRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Trainer not found. User ID: " + userId));

        TrainerModel trainerModel = modelMapper.map(trainerEntity, TrainerModel.class);
        trainerModel.setUserId(trainerEntity.getUser().getUserId());

        return trainerModel;
    }

    @Override
    public Resource getProfilePicture(Long trainerId) {
        TrainerEntity trainerEntity = trainerRepository.findById(trainerId).orElse(null);

        if (trainerEntity == null || trainerEntity.getProfilePicturePath() == null) {
            throw new NotFoundException("Profile picture not found.");
        }
        Path path = Paths.get(trainerEntity.getProfilePicturePath());
        Resource resource =  new FileSystemResource(path.toFile());

        return resource;
    }
}
