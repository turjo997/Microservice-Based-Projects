package com.bjit.trainingmanagementsystem.service.classroom;

import com.bjit.trainingmanagementsystem.models.classroom.PostCreateRequest;
import com.bjit.trainingmanagementsystem.models.classroom.PostModel;
import com.bjit.trainingmanagementsystem.models.classroom.PostUpdateModel;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ClassroomPostService {
    PostModel createPost(PostCreateRequest postCreateRequest);

    List<PostModel> getPostsByClassroomId(Long classroomId);

    PostModel updatePost(Long postId, PostUpdateModel postUpdateModel);

    Resource getPostFile(Long postId);
}
