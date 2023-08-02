package com.bjit.tss.controller;

import com.bjit.tss.model.response.ApiResponse;
import com.bjit.tss.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/upload/file-upload")
public class FileUploadController {

    private final FileService fileService;

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<?>> uploadImage(@RequestParam("profile-picture") MultipartFile image) {
        return fileService.uploadImage(image);
    }

    @PostMapping("/resume")
    public ResponseEntity<ApiResponse<?>> uploadResume(@RequestParam("resume") MultipartFile resume) {
        return fileService.uploadResume(resume);
    }
}