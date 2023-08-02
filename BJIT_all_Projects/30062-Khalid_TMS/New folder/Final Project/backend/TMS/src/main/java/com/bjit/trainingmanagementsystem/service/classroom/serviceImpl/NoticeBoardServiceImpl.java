package com.bjit.trainingmanagementsystem.service.classroom.serviceImpl;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.NoticeBoardEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.models.classroom.NoticeBoardModel;
import com.bjit.trainingmanagementsystem.repository.classroom.NoticeBoardRepository;
import com.bjit.trainingmanagementsystem.service.classroom.NoticeBoardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final ModelMapper modelMapper;

    @Override
    public NoticeBoardEntity createNoticeBoard(Long batchId) {
        NoticeBoardEntity noticeBoard = NoticeBoardEntity.builder()
                .batchId(batchId)
                .build();
        NoticeBoardEntity savedNoticeBoard = noticeBoardRepository.save(noticeBoard);

        return savedNoticeBoard;
    }

    @Override
    public NoticeBoardModel getNoticeBatchId(Long batchId) {
        NoticeBoardEntity noticeBoardEntity = noticeBoardRepository.findByBatchId(batchId)
                .orElseThrow(() -> new NotFoundException("Noticeboard not found with Batch ID: " + batchId));

        return modelMapper.map(noticeBoardEntity, NoticeBoardModel.class);
    }
}
