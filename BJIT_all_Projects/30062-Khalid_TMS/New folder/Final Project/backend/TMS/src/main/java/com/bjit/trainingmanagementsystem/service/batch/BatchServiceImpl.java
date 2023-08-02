package com.bjit.trainingmanagementsystem.service.batch;

import com.bjit.trainingmanagementsystem.entities.BatchEntity;
import com.bjit.trainingmanagementsystem.entities.classroomEntites.NoticeBoardEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.models.Roles.TrainerModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchAssignmentRequest;
import com.bjit.trainingmanagementsystem.models.batch.BatchModel;
import com.bjit.trainingmanagementsystem.models.batch.BatchUpdateModel;
import com.bjit.trainingmanagementsystem.models.classroom.ClassroomModel;
import com.bjit.trainingmanagementsystem.models.groupchat.GroupChatModel;
import com.bjit.trainingmanagementsystem.repository.batch.BatchRepository;
import com.bjit.trainingmanagementsystem.repository.role.TrainerRepository;
import com.bjit.trainingmanagementsystem.service.classroom.ClassroomService;
import com.bjit.trainingmanagementsystem.service.classroom.NoticeBoardService;
import com.bjit.trainingmanagementsystem.service.groupChat.GroupChatService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.bjit.trainingmanagementsystem.utils.BeanUtilsHelper.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepository;
    private final TrainerRepository trainerRepository;
    private final ClassroomService classroomService;
    private final NoticeBoardService noticeBoardService;
    private final GroupChatService groupChatService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BatchModel create(BatchModel batchModel) {

        BatchEntity batchEntity = BatchEntity.builder()
                .batchName(batchModel.getBatchName())
                .startDate(batchModel.getStartDate())
                .endDate(batchModel.getEndDate())
                .build();

        BatchEntity savedBatch = batchRepository.save(batchEntity);

        ClassroomModel classroomModel = classroomService.createClassroom(savedBatch.getBatchId());
        NoticeBoardEntity noticeBoardEntity = noticeBoardService.createNoticeBoard(savedBatch.getBatchId());
        GroupChatModel groupChatModel = groupChatService.createGroupChat(savedBatch.getBatchId());

        return modelMapper.map(savedBatch, BatchModel.class);
    }

    @Override
    public List<BatchModel> getAllBatches() {
        List<BatchEntity> batches = batchRepository.findAllByOrderByBatchIdDesc();
        return batches.stream()
                .map(batchEntity -> modelMapper.map(batchEntity, BatchModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainerModel> getTrainerListByBatch(Long batchId) {
        BatchEntity batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new NotFoundException("Batch not found. ID: " + batchId));
        List<TrainerEntity> trainerList = batch.getTrainers();

        return trainerList.stream()
                .map(trainerEntity -> modelMapper.map(trainerEntity, TrainerModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BatchModel getBatchById(Long batchId) {
        BatchEntity batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new NotFoundException("Batch not found.  ID: " + batchId));
        return modelMapper.map(batch, BatchModel.class);
    }

    @Override
    public BatchModel updateBatch(BatchUpdateModel batchUpdateModel, Long batchId) {
        BatchEntity batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new NotFoundException("Batch not found. ID: " + batchId));

        BeanUtils.copyProperties(batchUpdateModel, batch, getNullPropertyNames(batchUpdateModel));
        BatchEntity updatedBatch = batchRepository.save(batch);

        return modelMapper.map(updatedBatch, BatchModel.class);
    }

    @Override
    public BatchModel assignTrainer(BatchAssignmentRequest batchAssignmentRequest) {
        TrainerEntity trainer = trainerRepository.findById(batchAssignmentRequest.getTrainerId())
                .orElseThrow(() -> new NotFoundException("Trainer not found. ID: " + batchAssignmentRequest.getTrainerId()));

        BatchEntity batch = batchRepository.findById(batchAssignmentRequest.getBatchId())
                .orElseThrow(() -> new NotFoundException("Batch not found. ID: " + batchAssignmentRequest.getBatchId()));

        batch.getTrainers().add(trainer);
        BatchEntity savedBatch = batchRepository.save(batch);

        return modelMapper.map(savedBatch, BatchModel.class);
    }
}
