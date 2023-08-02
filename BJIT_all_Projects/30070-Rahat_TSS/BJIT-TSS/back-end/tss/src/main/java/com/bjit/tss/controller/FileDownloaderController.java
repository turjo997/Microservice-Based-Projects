package com.bjit.tss.controller;

import com.bjit.tss.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file-download")
public class FileDownloaderController {

    private final FileService fileService;

    @GetMapping("/image/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) throws IOException {
        return fileService.downloadImage(fileName);
    }

    @GetMapping("/resume/{fileName}")
    public ResponseEntity<?> downloadResumeAdmin(@PathVariable String fileName) throws IOException {
        return fileService.downloadResumeAdmin(fileName);
    }

    @GetMapping("/resume")
    public ResponseEntity<?> downloadResume() throws IOException {
        return fileService.downloadResume();
    }
}
