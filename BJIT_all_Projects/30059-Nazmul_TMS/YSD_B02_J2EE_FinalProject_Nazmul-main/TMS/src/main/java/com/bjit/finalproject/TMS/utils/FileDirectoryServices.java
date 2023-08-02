package com.bjit.finalproject.TMS.utils;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.net.MalformedURLException;


@Service
public class FileDirectoryServices {
    public String assignmentPathToSaveAndGet(){
        String directoryName = "TMS files\\files\\assignments"; // Specify the directory name
        String projectDirectory = System.getProperty("user.dir"); // Get the current project directory

        String directoryPath = projectDirectory + File.separator + directoryName;
        Path path = Paths.get(directoryPath);

        try {
            Files.createDirectories(path);
            System.out.println("Directory created successfully at: " + directoryPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directoryPath;
    }

    public String submissionPathToSaveAndGet(){
        String directoryName = "TMS files\\files\\submissions"; // Specify the directory name
        String projectDirectory = System.getProperty("user.dir"); // Get the current project directory

        String directoryPath = projectDirectory + File.separator + directoryName;
        Path path = Paths.get(directoryPath);

        try {
            Files.createDirectories(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directoryPath;
    }

    public String classroomFileToSaveAndGet(){
        String directoryName = "TMS files\\files\\classroom_files"; // Specify the desired directory name
        String projectDirectory = System.getProperty("user.dir"); // Get the current project directory

        String directoryPath = projectDirectory + File.separator + directoryName;
        Path path = Paths.get(directoryPath);

        try {
            Files.createDirectories(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directoryPath;
    }

    public ResponseEntity<Resource> getFile(String fileName){
        String projectDirectory = System.getProperty("user.dir"); // Get the current project directory
        String filePath = projectDirectory+"\\TMS files\\files\\assignments\\"+fileName;
        if(fileName.startsWith("Assignment_")){
            filePath = projectDirectory+"\\TMS files\\files\\assignments\\"+fileName;
        } else if( fileName.startsWith("Submission_")){
            filePath = projectDirectory+"\\TMS files\\files\\submissions\\"+fileName;
        } else if(fileName.startsWith("Classroom_")){
            filePath = projectDirectory+"\\TMS files\\files\\classroom_files\\"+fileName;
        }
        Path imagePath = Paths.get(filePath);

        // Verify that the file exists
        File file = imagePath.toFile();
        if (!file.exists() || file.isDirectory()) {
            return ResponseEntity.notFound().build();
        }

        // Create a Resource object from the file path
        Resource resource;
        try {
            resource = new UrlResource(imagePath.toUri());
        } catch (MalformedURLException e) {
            // Handle file not found or invalid file path error
            return ResponseEntity.notFound().build();
        }

        String contentDisposition = "attachment; filename=" + fileName;
        // Return the file as a response with appropriate headers
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // Adjust the content type based on your image type
                .body(resource);
    }

    public ResponseEntity<Resource> getImage(String fileName){
        String projectDirectory = System.getProperty("user.dir"); // Get the current project directory
        String filePath = projectDirectory+"\\TMS files\\images\\" + fileName;

        Path imagePath = Paths.get(filePath);
        String contentType = determineContentType(fileName);
        File file = imagePath.toFile();
        if (!file.exists() || file.isDirectory()) {
            return ResponseEntity.notFound().build();
        }

        // Create a Resource object from the file path
        Resource resource;
        try {
            resource = new UrlResource(imagePath.toUri());
        } catch (MalformedURLException e) {
            // Handle file not found or invalid file path error
            return ResponseEntity.notFound().build();
        }

        // Return the file as a response with appropriate headers
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType)) // Adjust the content type based on your image type
                .body(resource);
    }
    private String determineContentType(String filename) {
        String fileExtension = filename.substring(filename.lastIndexOf(".") + 1);

        switch (fileExtension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG_VALUE;
            case "png":
                return MediaType.IMAGE_PNG_VALUE;
            default:
                return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }
}
