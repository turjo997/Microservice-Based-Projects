package com.backend.tms.service;

import com.backend.tms.model.Classroom.CommentReqModel;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<Object> createComment(Long postId, CommentReqModel commentModel);
    ResponseEntity<Object> updateComment(Long commentId, CommentReqModel commentModel);
    ResponseEntity<Object> deleteComment(Long commentId);
}
