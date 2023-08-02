package com.bjit.tss.service;

import com.bjit.tss.model.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    ResponseEntity<ApiResponse<?>> uploadImage(MultipartFile image);

    ResponseEntity<ApiResponse<?>> uploadResume(MultipartFile resume);

    ResponseEntity<?> downloadImage(String fileName) throws IOException;

    ResponseEntity<?> downloadResumeAdmin(String fileName) throws IOException;


    ResponseEntity<?> downloadResume()throws IOException;
}