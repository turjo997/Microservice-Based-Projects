package com.bjit.trainingmanagementsystem.service.classroom.serviceImpl;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.NoticeBoardEntity;
import com.bjit.trainingmanagementsystem.entities.classroomEntites.NoticeEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.exception.UnauthorizedException;
import com.bjit.trainingmanagementsystem.models.classroom.NoticeCreateRequest;
import com.bjit.trainingmanagementsystem.models.classroom.NoticeModel;
import com.bjit.trainingmanagementsystem.models.classroom.NoticeUpdateModel;
import com.bjit.trainingmanagementsystem.repository.classroom.NoticeBoardRepository;
import com.bjit.trainingmanagementsystem.repository.classroom.NoticeRepository;
import com.bjit.trainingmanagementsystem.repository.role.TrainerRepository;
import com.bjit.trainingmanagementsystem.service.classroom.NoticeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.bjit.trainingmanagementsystem.utils.BeanUtilsHelper.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeBoardRepository noticeBoardRepository;
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public NoticeModel createNotice(NoticeCreateRequest noticeCreateRequest) {
        UserEntity userEntity = (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long authenticatedUserId = userEntity.getUserId();

        TrainerEntity trainerEntity = trainerRepository.findById(noticeCreateRequest.getTrainerId())
                .orElseThrow(() -> new NotFoundException("Trainer not found. ID: " + noticeCreateRequest.getTrainerId()));
        Long requestUserId = trainerEntity.getUser().getUserId();

        if (!authenticatedUserId.equals(requestUserId)) {
            throw new UnauthorizedException("You are not Authorized to perform this action");
        }

        NoticeEntity noticeEntity = NoticeEntity.builder()
                .textData(noticeCreateRequest.getTextData())
                .postDate(noticeCreateRequest.getPostDate())
                .noticeBoardId(noticeCreateRequest.getNoticeBoardId())
                .trainerId(noticeCreateRequest.getTrainerId())
                .trainerName(trainerEntity.getFullName())
                .noticeTitle(noticeCreateRequest.getNoticeTitle())
                .build();

        NoticeEntity savedNotice = noticeRepository.save(noticeEntity);
        NoticeBoardEntity noticeBoardEntity = noticeBoardRepository.findById(noticeCreateRequest.getNoticeBoardId()).orElseThrow();
        noticeBoardEntity.getNotices().add(savedNotice);
        NoticeBoardEntity updatedNoticeBoard = noticeBoardRepository.save(noticeBoardEntity);

        return modelMapper.map(savedNotice, NoticeModel.class);
    }

    @Override
    public List<NoticeModel> getNoticesByNoticeBoardId(Long noticeBoardId) {
        List<NoticeEntity> noticeEntityList = noticeRepository.findByNoticeBoardId(noticeBoardId);
        return noticeEntityList.stream()
                .map(noticeEntity -> modelMapper.map(noticeEntity, NoticeModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public NoticeModel updateNotice(Long noticeId, NoticeUpdateModel noticeUpdateModel) {
        NoticeEntity noticeEntity = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NotFoundException("Notice not found. ID: " + noticeId));

        BeanUtils.copyProperties(noticeUpdateModel, noticeEntity, getNullPropertyNames(noticeUpdateModel));
        NoticeEntity updatedNotice = noticeRepository.save(noticeEntity);

        return modelMapper.map(updatedNotice, NoticeModel.class);
    }
}
