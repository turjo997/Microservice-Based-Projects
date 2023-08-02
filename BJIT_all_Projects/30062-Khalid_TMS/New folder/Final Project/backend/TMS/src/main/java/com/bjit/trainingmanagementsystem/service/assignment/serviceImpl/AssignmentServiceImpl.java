package com.bjit.trainingmanagementsystem.service.assignment.serviceImpl;

import com.bjit.trainingmanagementsystem.entities.assignmentEntites.AssignmentEntity;
import com.bjit.trainingmanagementsystem.exception.FileUploadFailedException;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.models.assignment.AssignmentCreateRequest;
import com.bjit.trainingmanagementsystem.models.assignment.AssignmentResponseModel;
import com.bjit.trainingmanagementsystem.models.assignment.AssignmentUpdateModel;
import com.bjit.trainingmanagementsystem.repository.assignment.AssignmentRepository;
import com.bjit.trainingmanagementsystem.service.assignment.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.bjit.trainingmanagementsystem.utils.BeanUtilsHelper.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final ModelMapper modelMapper;
    private static final String ASSIGNMENT_DIR = "src/main/resources/assignments";

    @Override
    public AssignmentResponseModel create(AssignmentCreateRequest assignmentCreateRequest)  {

        AssignmentEntity assignmentEntity = AssignmentEntity.builder()
                .title(assignmentCreateRequest.getTitle())
                .description(assignmentCreateRequest.getDescription())
                .deadline(assignmentCreateRequest.getDeadline())
                .trainerId(assignmentCreateRequest.getTrainerId())
                .batchId(assignmentCreateRequest.getBatchId())
                .build();

        MultipartFile assignmentMultipartFile = assignmentCreateRequest.getAssignmentMultipartFile();

        if (assignmentMultipartFile != null && !assignmentMultipartFile.isEmpty()) {
            String fileName = assignmentMultipartFile.getOriginalFilename();
            Path path = Paths.get(ASSIGNMENT_DIR, fileName);
            try {
                Files.write(path, assignmentMultipartFile.getBytes());
            } catch (IOException ioException) {
                throw new FileUploadFailedException("file upload failed: "+ioException.getMessage());
            }
            assignmentEntity.setFilePath(path.toString());
        }
        AssignmentEntity savedAssignment = assignmentRepository.save(assignmentEntity);

        return modelMapper.map(savedAssignment, AssignmentResponseModel.class);
    }

    @Override
    public AssignmentResponseModel findAssignmentById(Long assignmentId) {
        AssignmentEntity assignmentEntity = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Assignment not found with ID: " + assignmentId));
        return modelMapper.map(assignmentEntity, AssignmentResponseModel.class);
    }

    @Override
    public List<AssignmentResponseModel> findAssignmentsByBatchId(Long batchId) {
        List<AssignmentEntity> assignmentEntities = assignmentRepository.findByBatchId(batchId);
        return assignmentEntities.stream()
                .map(assignmentEntity -> modelMapper.map(assignmentEntity, AssignmentResponseModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentResponseModel updateAssignment(AssignmentUpdateModel assignmentUpdateModel, Long assignmentId) {
        AssignmentEntity assignmentEntity = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Assignment not found with ID: " + assignmentId));

        assignmentEntity.setTitle(assignmentUpdateModel.getTitle());
        assignmentEntity.setDescription(assignmentUpdateModel.getDescription());
        assignmentEntity.setDeadline(assignmentUpdateModel.getDeadline());

        MultipartFile assignmentMultipartFile = assignmentUpdateModel.getAssignmentMultipartFile();

        if (assignmentMultipartFile != null && !assignmentMultipartFile.isEmpty()) {
            String fileName = assignmentMultipartFile.getOriginalFilename();
            Path path = Paths.get(ASSIGNMENT_DIR, fileName);
            try {
                Files.write(path, assignmentMultipartFile.getBytes());
            } catch (IOException ioException) {
                throw new FileUploadFailedException("file upload failed: "+ioException.getMessage());
            }

            assignmentEntity.setFilePath(path.toString());
        }

        AssignmentEntity updatedAssignment = assignmentRepository.save(assignmentEntity);
        return modelMapper.map(updatedAssignment, AssignmentResponseModel.class);
    }

    @Override
    public byte[] getAssignmentFileData(Long assignmentId) throws IOException {
        AssignmentEntity assignmentEntity = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Assignment not found."));

        if (assignmentEntity.getFilePath() == null) {
            throw new NotFoundException("Assignment file not found.");
        }

        Path filePath = Paths.get(assignmentEntity.getFilePath());
        return Files.readAllBytes(filePath);
    }

    @Override
    public Resource downloadAssignmentById(Long assignmentId) {
        AssignmentEntity assignment = assignmentRepository.findById(assignmentId)
                .orElse(null);
        if (assignment == null) {
            return null;
        }
        Path file = Paths.get(assignment.getFilePath());

        return new FileSystemResource(file);
    }

    @Override
    public Resource getAssignmentFileResource(Long assignmentId) {
        AssignmentEntity assignmentEntity = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Assignment not found"));
        String filePath = assignmentEntity.getFilePath();

        return new FileSystemResource(filePath);
    }

}
