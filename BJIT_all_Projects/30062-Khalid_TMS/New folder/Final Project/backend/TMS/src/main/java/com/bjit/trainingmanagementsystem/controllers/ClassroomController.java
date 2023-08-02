package com.bjit.trainingmanagementsystem.controllers;

import com.bjit.trainingmanagementsystem.models.classroom.*;
import com.bjit.trainingmanagementsystem.service.classroom.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classroom")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomPostService classroomPostService;
    private final ClassroomCommentService classroomCommentService;
    private final NoticeService noticeService;
    private final ClassroomService classroomService;
    private final NoticeBoardService noticeBoardService;


    @GetMapping("/{batchId}")
    public ResponseEntity<ClassroomModel> getClassroomByBatchId(@PathVariable Long batchId) {
        return new ResponseEntity<>(classroomService.getClassroomByBatchId(batchId), HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<PostModel> createPost(@ModelAttribute PostCreateRequest postCreateRequest) {
        return new ResponseEntity<>(classroomPostService.createPost(postCreateRequest), HttpStatus.OK);
    }

    @GetMapping("/post-list/{classroomId}")
    public ResponseEntity<List<PostModel>> getPostsByClassroomId(@PathVariable Long classroomId) {
        List<PostModel> posts = classroomPostService.getPostsByClassroomId(classroomId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/post-update/{postId}")
    public ResponseEntity<PostModel> updatePost(@PathVariable Long postId, @RequestBody PostUpdateModel postUpdateModel) {
        return new ResponseEntity<>(classroomPostService.updatePost(postId, postUpdateModel), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentModel> createComment(@RequestBody CommentModel commentModel){
        return new ResponseEntity<>(classroomCommentService.createComment(commentModel), HttpStatus.OK);
    }


    @GetMapping("/comment-list/{postId}")
    public ResponseEntity<List<CommentModel>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentModel> comments = classroomCommentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/comment-update/{commentId}")
    public ResponseEntity<CommentModel> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateModel commentUpdateModel) {
        return new ResponseEntity<>(classroomCommentService.updateComment(commentId, commentUpdateModel), HttpStatus.OK);
    }


    @GetMapping("/notice-board/{batchId}")
    public ResponseEntity<NoticeBoardModel> getNoticeBoardById(@PathVariable Long batchId) {
        return new ResponseEntity<>(noticeBoardService.getNoticeBatchId(batchId), HttpStatus.OK);
    }

    @PostMapping("/notice")
    public ResponseEntity<Object> createNotice(@RequestBody NoticeCreateRequest noticeCreateRequest){
        return new ResponseEntity<>(noticeService.createNotice(noticeCreateRequest), HttpStatus.OK);
    }

    @GetMapping("/notice-list/{noticeBoardId}")
    public ResponseEntity<List<NoticeModel>> getNoticesByClassroomId(@PathVariable Long noticeBoardId) {
        List<NoticeModel> noticeList = noticeService.getNoticesByNoticeBoardId(noticeBoardId);
        return new ResponseEntity<>(noticeList, HttpStatus.OK);
    }

    @PutMapping("/notice-update/{noticeId}")
    public ResponseEntity<NoticeModel> updateNotice(@PathVariable Long noticeId, @RequestBody NoticeUpdateModel noticeUpdateModel) {
        return new ResponseEntity<>(noticeService.updateNotice(noticeId, noticeUpdateModel), HttpStatus.OK);
    }

    @GetMapping("/download/{postId}")
    public ResponseEntity<Resource> downloadPost(@PathVariable Long postId) {
        Resource resource = classroomPostService.getPostFile(postId);

        if (resource != null && resource.exists() && resource.isReadable()) {
            String filename = "post.zip";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
