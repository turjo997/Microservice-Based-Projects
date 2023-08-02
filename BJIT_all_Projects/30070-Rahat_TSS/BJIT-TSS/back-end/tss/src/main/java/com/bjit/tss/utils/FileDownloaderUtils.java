package com.bjit.tss.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloaderUtils {
    private Path foundFile;

    public Resource getFileAsResourceImage(String fileCode) throws IOException {
        Path dirPath = Paths.get("src/main/resources/static/image");

        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }

    public Resource getFileAsResourceResume(String fileName) throws IOException {
        Path dirPath = Paths.get("src/main/resources/static/resume");

        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileName)) {
                foundFile = file;
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }
}