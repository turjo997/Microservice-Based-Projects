package com.bjit.trainingmanagementsystem.service.classroom.serviceImpl;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.ClassroomCommentEntity;
import com.bjit.trainingmanagementsystem.entities.classroomEntites.ClassroomPostEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TraineeEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.exception.UnauthorizedException;
import com.bjit.trainingmanagementsystem.models.classroom.CommentModel;
import com.bjit.trainingmanagementsystem.models.classroom.CommentUpdateModel;
import com.bjit.trainingmanagementsystem.repository.classroom.ClassroomCommentRepository;
import com.bjit.trainingmanagementsystem.repository.classroom.ClassroomPostRepository;
import com.bjit.trainingmanagementsystem.repository.role.TraineeRepository;
import com.bjit.trainingmanagementsystem.repository.role.TrainerRepository;
import com.bjit.trainingmanagementsystem.service.classroom.ClassroomCommentService;
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
public class ClassroomCommentImpl implements ClassroomCommentService {

    private final ClassroomCommentRepository classroomCommentRepository;
    private final ClassroomPostRepository classroomPostRepository;
    private final ModelMapper modelMapper;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;

    @Override
    @Transactional
    public CommentModel createComment(CommentModel commentModel) {

        UserEntity userEntity = (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long authenticatedUserId = userEntity.getUserId();
        Long requestUserId = commentModel.getUserId();

        if (!authenticatedUserId.equals(requestUserId)) {
            throw new UnauthorizedException("You are not Authorized to perform this action");
        }

        ClassroomCommentEntity classroomCommentEntity = ClassroomCommentEntity.builder()
                .commentData(commentModel.getCommentData())
                .commentTime(commentModel.getCommentTime())
                .postId(commentModel.getPostId())
                .userId(commentModel.getUserId())
                .build();

        if (userEntity.getRoleEntity().getRoleName().equals("TRAINER")) {
            TrainerEntity trainerEntity = trainerRepository.findByUserId(userEntity.getUserId())
                    .orElseThrow(() -> new NotFoundException("Trainer not found with User ID: " + userEntity.getUserId()));
            classroomCommentEntity.setName(trainerEntity.getFullName());
        } else {
            TraineeEntity traineeEntity = traineeRepository.findByUserId(userEntity.getUserId())
                    .orElseThrow(() -> new NotFoundException("Trainee not found with User ID: " + userEntity.getUserId()));
            classroomCommentEntity.setName(traineeEntity.getFullName());
        }

        ClassroomCommentEntity savedComment = classroomCommentRepository.save(classroomCommentEntity);
        ClassroomPostEntity classroomPostEntity = classroomPostRepository.findById(commentModel.getPostId()).orElseThrow();
        classroomPostEntity.getComments().add(savedComment);
        ClassroomPostEntity updatePost = classroomPostRepository.save(classroomPostEntity);

        return modelMapper.map(savedComment, CommentModel.class);
    }

    @Override
    public List<CommentModel> getCommentsByPostId(Long postId) {
        List<ClassroomCommentEntity> commentEntities = classroomCommentRepository.findByPostId(postId);
        return commentEntities.stream()
                .map(commentEntity -> modelMapper.map(commentEntity, CommentModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentModel updateComment(Long commentId, CommentUpdateModel commentUpdateModel) {
        ClassroomCommentEntity classroomCommentEntity = classroomCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found with ID: " + commentId));

        BeanUtils.copyProperties(commentUpdateModel, classroomCommentEntity, getNullPropertyNames(commentUpdateModel));
        ClassroomCommentEntity updatedComment = classroomCommentRepository.save(classroomCommentEntity);

        return modelMapper.map(updatedComment, CommentModel.class);
    }
}
