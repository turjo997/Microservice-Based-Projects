package com.bjit.tss.service.Implementation;

import com.bjit.tss.entity.ApplicantEntity;
import com.bjit.tss.entity.ResourceEntity;
import com.bjit.tss.model.ResourceModel;
import com.bjit.tss.repository.ResourceRepository;
import com.bjit.tss.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;


    @Override
    public ResponseEntity<Object> saveResourceByApplicantId(Long applicantId, MultipartFile photo, MultipartFile cv) {
        try {
            byte[] photoData = photo.getBytes();
            byte[] cvData = cv.getBytes();

            Optional<ResourceEntity> existingResourceOptional = resourceRepository.findByApplicant_ApplicantId(applicantId);
            if (existingResourceOptional.isPresent()) {
                ResourceEntity existingResource = existingResourceOptional.get();
                existingResource.setPhotoData(photoData);
                existingResource.setCvData(cvData);
                resourceRepository.save(existingResource);

                ResourceModel updatedResourceModel = new ResourceModel(
                        existingResource.getRsId(),
                        existingResource.getApplicant(),
                        existingResource.getPhotoData(),
                        existingResource.getCvData()
                );

                return ResponseEntity.status(HttpStatus.OK).body(updatedResourceModel);
            } else {
                ApplicantEntity applicantEntity = ApplicantEntity.builder().applicantId(applicantId).build();
                ResourceEntity newResource = ResourceEntity.builder()
                        .applicant(applicantEntity)
                        .photoData(photoData)
                        .cvData(cvData)
                        .build();

                ResourceEntity savedResource = resourceRepository.save(newResource);

                ResourceModel savedResourceModel = new ResourceModel(
                        savedResource.getRsId(),
                        savedResource.getApplicant(),
                        savedResource.getPhotoData(),
                        savedResource.getCvData()
                );

                return ResponseEntity.status(HttpStatus.CREATED).body(savedResourceModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving the resource");
        }
    }


    @Override
    public ResponseEntity<Object> getResourceByApplicantId(Long applicantId) {
        Optional<ResourceEntity> resourceOptional = resourceRepository.findByApplicant_ApplicantId(applicantId);

        if (resourceOptional.isPresent()) {
            ResourceEntity resourceEntity = resourceOptional.get();
            ResourceModel resourceModel = new ResourceModel(
                    resourceEntity.getRsId(),
                    resourceEntity.getApplicant(),
                    resourceEntity.getPhotoData(),
                    resourceEntity.getCvData()
            );
            return ResponseEntity.ok(resourceModel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found for the given applicantId");
        }
    }

    @Override
    public ResponseEntity<Object> deleteResourceByApplicantId(Long applicantId) {
        Optional<ResourceEntity> resourceOptional = resourceRepository.findByApplicant_ApplicantId(applicantId);

        if (resourceOptional.isPresent()) {
            resourceRepository.deleteById(resourceOptional.get().getRsId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found for the given applicantId");
        }
    }
}
