package com.bjit.trainingmanagementsystem.service.classroom.serviceImpl;

import com.bjit.trainingmanagementsystem.entities.classroomEntites.ClassroomEntity;
import com.bjit.trainingmanagementsystem.entities.classroomEntites.ClassroomPostEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.TrainerEntity;
import com.bjit.trainingmanagementsystem.entities.roleEntites.UserEntity;
import com.bjit.trainingmanagementsystem.exception.FileUploadFailedException;
import com.bjit.trainingmanagementsystem.exception.NotFoundException;
import com.bjit.trainingmanagementsystem.exception.UnauthorizedException;
import com.bjit.trainingmanagementsystem.models.classroom.PostCreateRequest;
import com.bjit.trainingmanagementsystem.models.classroom.PostModel;
import com.bjit.trainingmanagementsystem.models.classroom.PostUpdateModel;
import com.bjit.trainingmanagementsystem.repository.classroom.ClassroomPostRepository;
import com.bjit.trainingmanagementsystem.repository.classroom.ClassroomRepository;
import com.bjit.trainingmanagementsystem.repository.role.TrainerRepository;
import com.bjit.trainingmanagementsystem.service.classroom.ClassroomPostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
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

import static com.bjit.trainingmanagementsystem.utils.BeanUtilsHelper.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class ClassroomPostServiceImpl implements ClassroomPostService {

    private final ClassroomPostRepository classroomPostRepository;
    private final ClassroomRepository classroomRepository;
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;
    private static final String POST_DIR = "src/main/resources/post";

    @Override
    @Transactional
    public PostModel createPost(PostCreateRequest postCreateRequest) {

        UserEntity userEntity = (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Long authenticatedUserId = userEntity.getUserId();

        TrainerEntity trainerEntity = trainerRepository.findById(postCreateRequest.getTrainerId())
                .orElseThrow(() -> new NotFoundException("Trainer not found. ID: " + postCreateRequest.getTrainerId()));
        Long requestUserId = trainerEntity.getUser().getUserId();

        if (!authenticatedUserId.equals(requestUserId)) {
            throw new UnauthorizedException("You are not Authorized to perform this action");
        }

        ClassroomPostEntity classroomPostEntity = ClassroomPostEntity.builder()
                .textData(postCreateRequest.getTextData())
                .postDate(postCreateRequest.getPostDate())
                .trainerId(postCreateRequest.getTrainerId())
                .classroomId(postCreateRequest.getClassroomId())
                .build();

        classroomPostEntity.setTrainerName(trainerEntity.getFullName());
        MultipartFile postFile = postCreateRequest.getPostFile();

        if (postFile != null && !postFile.isEmpty()) {
            String fileName = postFile.getOriginalFilename();
            Path path = Paths.get(POST_DIR, fileName);
            try {
                Files.write(path, postFile.getBytes());
            } catch (IOException ioException) {
                throw new FileUploadFailedException("file upload failed: "+ioException.getMessage());
            }

            classroomPostEntity.setFilePath(path.toString());
        }

        ClassroomPostEntity savedPost = classroomPostRepository.save(classroomPostEntity);

        ClassroomEntity classroomEntity = classroomRepository.findById(postCreateRequest.getClassroomId())
                .orElseThrow(() -> new NotFoundException("Classroom not found. ID: " + postCreateRequest.getClassroomId()));

        classroomEntity.getPosts().add(classroomPostEntity);
        ClassroomEntity updatedClassroom = classroomRepository.save(classroomEntity);

        return modelMapper.map(savedPost, PostModel.class);

    }

    @Override
    public List<PostModel> getPostsByClassroomId(Long classroomId) {
        List<ClassroomPostEntity> postEntityList = classroomPostRepository.findByClassroomId(classroomId);
        return postEntityList.stream()
                .map(postEntity -> modelMapper.map(postEntity, PostModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostModel updatePost(Long postId, PostUpdateModel postUpdateModel) {
        ClassroomPostEntity postEntity = classroomPostRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found with ID: " + postId));

        BeanUtils.copyProperties(postUpdateModel, postEntity, getNullPropertyNames(postUpdateModel));
        ClassroomPostEntity updatedPost = classroomPostRepository.save(postEntity);

        return modelMapper.map(updatedPost, PostModel.class);
    }

    @Override
    public Resource getPostFile(Long postId) {
        System.out.println("yo----------------->");
        ClassroomPostEntity classroomPostEntity = classroomPostRepository.findById(postId)
                .orElse(null);
        if (classroomPostEntity == null) {
            return null;
        }
        Path file = Paths.get(classroomPostEntity.getFilePath());

        return new FileSystemResource(file);
    }
}
