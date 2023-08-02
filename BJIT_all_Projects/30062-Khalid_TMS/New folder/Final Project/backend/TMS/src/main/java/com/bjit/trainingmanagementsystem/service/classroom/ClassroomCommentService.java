package com.bjit.trainingmanagementsystem.service.classroom;

import com.bjit.trainingmanagementsystem.models.classroom.CommentModel;
import com.bjit.trainingmanagementsystem.models.classroom.CommentUpdateModel;

import java.util.List;

public interface ClassroomCommentService {
    CommentModel createComment(CommentModel commentModel);

    List<CommentModel> getCommentsByPostId(Long postId);

    CommentModel updateComment(Long commentId, CommentUpdateModel commentUpdateModel);
}
