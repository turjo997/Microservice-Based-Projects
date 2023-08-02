package com.bjit.trainingmanagementsystem.service.assignment.serviceImpl;

import com.bjit.trainingmanagementsystem.entities.assignmentEntites.AssignmentEntity;
import com.bjit.trainingmanagementsystem.entities.assignmentEntites.SubmissionEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TraineeEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import com.bjit.trainingmanagementsystem.exception.FileUploadFailedException;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.exception.UnauthorizedException;
import com.bjit.trainingmanagementsystem.models.assignment.SubmissionCreateRequest;
import com.bjit.trainingmanagementsystem.models.assignment.SubmissionResponseModel;
import com.bjit.trainingmanagementsystem.repository.assignment.AssignmentRepository;
import com.bjit.trainingmanagementsystem.repository.assignment.SubmissionRepository;
import com.bjit.trainingmanagementsystem.repository.role.TraineeRepository;
import com.bjit.trainingmanagementsystem.service.assignment.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {


    private final SubmissionRepository submissionRepository;
    private final TraineeRepository traineeRepository;
    private final ModelMapper modelMapper;
    private final AssignmentRepository assignmentRepository;

    private static final String SUBMISSION_DIR = "src/main/resources/submissions";

    @Override
    @Transactional
    public SubmissionResponseModel createSubmission(SubmissionCreateRequest submissionCreateRequest) {

        UserEntity userEntity = (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long authenticatedUserId = userEntity.getUserId();

        TraineeEntity traineeEntity = traineeRepository.findById(submissionCreateRequest.getTraineeId())
                .orElseThrow(() -> new NotFoundException("Trainee not found. ID: " + submissionCreateRequest.getTraineeId()));

        Long requestUserId = traineeEntity.getUser().getUserId();

        if (!authenticatedUserId.equals(requestUserId)) {
            throw new UnauthorizedException("You are not Authorized to perform this action");
        }

        SubmissionEntity submissionEntity = SubmissionEntity.builder()
                .submissionTime(submissionCreateRequest.getSubmissionTime())
                .assignmentId(submissionCreateRequest.getAssignmentId())
                .traineeId(submissionCreateRequest.getTraineeId())
                .turnedIn("true")
                .build();

        MultipartFile submissionMultipartFile = submissionCreateRequest.getSubmissionMultipartFile();

        if (submissionMultipartFile != null && !submissionMultipartFile.isEmpty()) {
            String fileName = submissionMultipartFile.getOriginalFilename();
            Path path = Paths.get(SUBMISSION_DIR, fileName);
            try {
                Files.write(path, submissionMultipartFile.getBytes());
                submissionEntity.setFilePath(path.toString());
            } catch (IOException ioException) {
                throw new FileUploadFailedException("File upload failed: "+ioException.getMessage());
            }
        }

        SubmissionEntity savedSubmission = submissionRepository.save(submissionEntity);
        AssignmentEntity assignmentEntity = assignmentRepository.findById(submissionCreateRequest.getAssignmentId())
                .orElseThrow(
                        () -> new NotFoundException("Assignment not found. ID: " + submissionCreateRequest.getAssignmentId())
                );

        assignmentEntity.getSubmissions().add(savedSubmission);
        AssignmentEntity updatedAssignment = assignmentRepository.save(assignmentEntity);

        return modelMapper.map(savedSubmission, SubmissionResponseModel.class);
    }

    @Override
    public SubmissionResponseModel getSubmissionById(Long submissionId) {
        SubmissionEntity submissionEntity = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NotFoundException("Submission not found. ID: " + submissionId));
        return modelMapper.map(submissionEntity, SubmissionResponseModel.class);
    }

    @Override
    public List<SubmissionResponseModel> getSubmissionsByAssignmentId(Long assignmentId) {
        List<SubmissionEntity> submissionEntities = submissionRepository.findByAssignmentId(assignmentId);

        List<SubmissionResponseModel> submissionResponses = submissionEntities.stream()
                .map(submissionEntity -> {
                    SubmissionResponseModel responseModel = modelMapper.map(submissionEntity, SubmissionResponseModel.class);
                    TraineeEntity traineeEntity = traineeRepository.findById(responseModel.getTraineeId()).orElse(null);
                    if (traineeEntity != null) {
                        String traineeName = traineeEntity.getFullName();
                        responseModel.setTraineeName(traineeName);
                    }
                    AssignmentEntity assignmentEntity = assignmentRepository.findById(responseModel.getAssignmentId()).orElse(null);
                    if (assignmentEntity != null) {
                        responseModel.setAssignmentTitle(assignmentEntity.getTitle());
                    }
                    return responseModel;
                })
                .collect(Collectors.toList());

        return submissionResponses;
    }

    @Override
    public SubmissionResponseModel getTurnedInStatus(Long assignmentId, Long traineeId) {
        List<SubmissionEntity> submissionEntityList = submissionRepository.findByAssignmentIdAndTraineeId(assignmentId, traineeId);
        SubmissionEntity submissionEntity = submissionEntityList.get(0);
        if (submissionEntity == null) {
            throw new NotFoundException("Submission not found with Assignment ID:"+ assignmentId+ "Trainee ID: "+traineeId);
        }
        return modelMapper.map(submissionEntity, SubmissionResponseModel.class);
    }

    @Override
    public Resource downloadSubmissionById(Long submissionId) {
        SubmissionEntity submissionEntity = submissionRepository.findById(submissionId)
                .orElse(null);
        if (submissionEntity == null) {
            return null;
        }
        System.out.println("--------------------->"+submissionEntity.getFilePath());
        try {
            Path file = Paths.get(submissionEntity.getFilePath());
            return new FileSystemResource(file);
        } catch (Exception ex) {
            throw new NotFoundException("submission not found "+ex.getMessage());
        }

    }
}
