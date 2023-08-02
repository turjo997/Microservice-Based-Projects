package com.backend.tms.service.Impl;

import com.backend.tms.entity.*;
import com.backend.tms.exception.custom.*;
import com.backend.tms.model.Classroom.NoticeResModel;
import com.backend.tms.model.Classroom.PostMessageReqModel;
import com.backend.tms.repository.*;
import com.backend.tms.utlis.AppConstants;
import com.backend.tms.utlis.FileService;
import org.apache.commons.io.FileUtils;
import com.backend.tms.model.Classroom.PostReqModel;
import com.backend.tms.model.Classroom.PostResModel;
import com.backend.tms.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    private final PostRepository postRepository;
    private final BatchRepository batchRepository;
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;



    @Override
    public ResponseEntity<Object> createPost( PostReqModel postModel) {
        try {
            // Validate if the associated classroom exists
            ClassroomEntity classroomEntity = classroomRepository.findById(postModel.getClassroomId())
                    .orElseThrow(() -> new ClassroomNotFoundException("Classroom not found"));

            // Validate if the associated trainer exists
            TrainerEntity trainerEntity = trainerRepository.findById(postModel.getTrainerId())
                    .orElseThrow(() -> new TrainerNotFoundException("Trainer not found"));

            MultipartFile file = postModel.getFile();
            String fileUrl = null;
            if (file != null && !file.isEmpty()) {
                fileUrl = FileService.uploadFile(file, AppConstants.POST_UPLOAD_DIR );
            }

            PostEntity postEntity = modelMapper.map(postModel, PostEntity.class);
            if (fileUrl != null) {
                postEntity.setFileUrl(fileUrl);
            }
            //adding current time
            Date currentTime = new Date();
            postEntity.setCreatedTime(currentTime);

            PostEntity createdPost = postRepository.save(postEntity);
            classroomEntity.getPosts().add(createdPost);
            classroomRepository.save(classroomEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create post");
        }
    }

    @Override
    public ResponseEntity<Object> getPost(Long postId) {
        try {
            PostEntity postEntity = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException("Post not found"));

            PostResModel postModel = modelMapper.map(postEntity, PostResModel.class);

            return ResponseEntity.ok(postModel);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve post");
        }
    }

    @Override
    public ResponseEntity<Object> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        if (postEntities.isEmpty()) {
            throw new PostNotFoundException("No posts published yet!");
        }

        // Convert List<PostEntity> to List<PostResModel> using ModelMapper
        List<PostResModel> postResModels = postEntities.stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());

        return new ResponseEntity<>(postResModels, HttpStatus.OK);
    }

    private PostResModel convertToModel(PostEntity postEntity) {
        PostResModel postResModel = modelMapper.map(postEntity, PostResModel.class);

        if (postEntity.getTrainerId() != null) {
            TrainerEntity trainer = trainerRepository.findById(postEntity.getTrainerId())
                    .orElseThrow(() -> new TrainerNotFoundException("Trainer not found with ID: " + postEntity.getTrainerId()));
            postResModel.setSenderName(trainer.getFullName());
        }

        return postResModel;
    }



    @Override
    public ResponseEntity<Object> updatePost(Long postId, PostReqModel postModel) {
        try {
            PostEntity postEntity = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + postId));

            //updating the post Entity
            postEntity.setTitle(postModel.getTitle());
            postEntity.setClassroomId(postModel.getClassroomId());
            postEntity.setTrainerId(postModel.getTrainerId());

            MultipartFile file = postModel.getFile();
            if (file != null && !file.isEmpty()) {
                String fileUrl = FileService.uploadFile(file, AppConstants.POST_UPLOAD_DIR );
                if (fileUrl == null) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file");
                }
                postEntity.setFileUrl(fileUrl);
            }
            postRepository.save(postEntity);
            return ResponseEntity.status(HttpStatus.OK).body("Post updated successfully");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update post");
        }
    }

    @Override
    public ResponseEntity<Object> downloadPostFile(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(()->new PostNotFoundException("post not found"));
       // if(postEntity.getFileUrl()==null){throw FileNotFoundException("File not found for download")}
       // }
       try{
           File file = new File(postEntity.getFileUrl());
           String fileContents = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
           // Create a new file in the specified directory
           String fileName = StringUtils.cleanPath(file.getName());
           File destinationDir = new File(AppConstants.POST_DOWNLOAD_DIR);
           if (!destinationDir.exists()) {
               destinationDir.mkdirs(); // Create the directory if it doesn't exist
           }
           File destinationFile = new File(destinationDir, fileName);
           FileUtils.writeStringToFile(destinationFile, fileContents, StandardCharsets.UTF_8);
           String message = "Download successful. File saved to: " + destinationFile.getAbsolutePath();
           return ResponseEntity.ok(message);
       }catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read or save the file");
        }
    }


    @Override
    public ResponseEntity<Object> getAllPostsByClassroom(Long classroomId) {
        List<PostEntity> postEntityList = postRepository.findByClassroomId(classroomId);
        Map<String, Object> response = new HashMap<>();
        response.put("Total Post", postEntityList.size());

        List<Map<String, Object>> postsWithTrainerName = new ArrayList<>();
        for (PostEntity postEntity : postEntityList) {
            Map<String, Object> postWithTrainerName = new HashMap<>();
            postWithTrainerName.put("id", postEntity.getId());
            postWithTrainerName.put("trainerId", postEntity.getTrainerId());
            postWithTrainerName.put("classroomId", postEntity.getClassroomId());
            postWithTrainerName.put("title", postEntity.getTitle());
            postWithTrainerName.put("createdTime", postEntity.getCreatedTime());
            postWithTrainerName.put("fileUrl", postEntity.getFileUrl());
            postWithTrainerName.put("comments", postEntity.getComments());

            // Fetch the trainer's name using trainerRepository.findById(trainerId)
            Optional<TrainerEntity> trainerOptional = trainerRepository.findById(postEntity.getTrainerId());
            if (trainerOptional.isPresent()) {
                TrainerEntity trainerEntity = trainerOptional.get();
                postWithTrainerName.put("trainerName", trainerEntity.getFullName());
            } else {
                postWithTrainerName.put("trainerName", "Trainer not found"); // Or any other default value
            }

            postsWithTrainerName.add(postWithTrainerName);
        }

        response.put("Posts", postsWithTrainerName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
