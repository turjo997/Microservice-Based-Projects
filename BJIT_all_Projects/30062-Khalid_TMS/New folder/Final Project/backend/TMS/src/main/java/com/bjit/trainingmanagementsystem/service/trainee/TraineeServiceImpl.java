package com.bjit.trainingmanagementsystem.service.trainee;

import com.bjit.trainingmanagementsystem.entities.roleEntites.TraineeEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.exception.UnauthorizedException;
import com.bjit.trainingmanagementsystem.models.Roles.TraineeModel;
import com.bjit.trainingmanagementsystem.models.Roles.TraineeUpdateModel;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchAssignmentRequest;
import com.bjit.trainingmanagementsystem.repository.role.TraineeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.bjit.trainingmanagementsystem.utils.BeanUtilsHelper.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TraineeModel> getTrainees() {
        List<TraineeEntity> trainees = traineeRepository.findAll();

        return trainees.stream()
                .map(traineeEntity -> {
                    TraineeModel traineeModel = modelMapper.map(traineeEntity, TraineeModel.class);
                    traineeModel.setUserId(traineeEntity.getUser().getUserId());
                    return traineeModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TraineeModel> getTraineesByBatchId(Long batchId) {
        List<TraineeEntity> trainees = traineeRepository.findByBatchId(batchId);

        return trainees.stream()
                .map(traineeEntity -> {
                    TraineeModel traineeModel = modelMapper.map(traineeEntity, TraineeModel.class);
                    traineeModel.setUserId(traineeEntity.getUser().getUserId());
                    return traineeModel;
                })
                .collect(Collectors.toList());
    }
    @Override
    public TraineeUpdateModel updateTrainee(TraineeUpdateModel traineeUpdateModel, Long traineeId) {

        UserEntity userEntity = (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long authenticatedUserId = userEntity.getUserId();

        TraineeEntity traineeEntity = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new NotFoundException("Trainee not found. ID: " + traineeId));

        Long userId = traineeEntity.getUser().getUserId();

        if (!authenticatedUserId.equals(userId)) {
            throw new UnauthorizedException("You are not Authorized to perform this action");
        }

        BeanUtils.copyProperties(traineeUpdateModel, traineeEntity, getNullPropertyNames(traineeUpdateModel));
        TraineeEntity updatedTrainee = traineeRepository.save(traineeEntity);

        return modelMapper.map(updatedTrainee, TraineeUpdateModel.class);
    }

    @Override
    public List<TraineeModel> getUnassignedTrainees() {
        List<TraineeEntity> unassignedTrainees = traineeRepository.findByBatchIdIsNull();

        return unassignedTrainees.stream()
                .map(traineeEntity -> {
                    TraineeModel traineeModel = modelMapper.map(traineeEntity, TraineeModel.class);
                    traineeModel.setUserId(traineeEntity.getUser().getUserId());
                    return traineeModel;
                })
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public TraineeModel assignBatch(BatchAssignmentRequest batchAssignmentRequest) {
        TraineeEntity traineeEntity = traineeRepository.findById(batchAssignmentRequest.getTraineeId())
                .orElseThrow(() -> new NotFoundException("Trainee not found.  ID: " + batchAssignmentRequest.getTraineeId()));

        traineeEntity.setBatchId(batchAssignmentRequest.getBatchId());

        TraineeEntity savedTrainee = traineeRepository.save(traineeEntity);

        TraineeModel traineeModel = modelMapper.map(savedTrainee, TraineeModel.class);
        traineeModel.setUserId(traineeEntity.getUser().getUserId());

        return traineeModel;
    }

    @Override
    public TraineeModel findTraineeById(Long traineeId) {
        TraineeEntity traineeEntity = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new NotFoundException("Trainee not found.  ID: " + traineeId));

        TraineeModel traineeModel = modelMapper.map(traineeEntity, TraineeModel.class);
        traineeModel.setUserId(traineeEntity.getUser().getUserId());

        return traineeModel;
    }


    @Override
    public TraineeModel findTraineeByUserId(Long userId) {
        TraineeEntity traineeEntity = traineeRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Trainee not found with User ID: " + userId));

        TraineeModel traineeModel = modelMapper.map(traineeEntity, TraineeModel.class);
        traineeModel.setUserId(traineeEntity.getUser().getUserId());

        return traineeModel;
    }

    @Override
    public Resource getProfilePicture(Long traineeId) {
        TraineeEntity traineeEntity = traineeRepository.findById(traineeId).orElse(null);

        if (traineeEntity == null || traineeEntity.getProfilePicturePath() == null) {
            throw new NotFoundException("Profile picture not found.");
        }
        Path path = Paths.get(traineeEntity.getProfilePicturePath());
        Resource resource =  new FileSystemResource(path.toFile());

        return resource;
    }
}
