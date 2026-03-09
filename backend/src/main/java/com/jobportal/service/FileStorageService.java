package com.jobportal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads/resumes/";

    public String saveFile(MultipartFile file) throws IOException {

        File directory = new File(uploadDir);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // safer path concatenation
        File destination = new File(uploadDir + "/" + fileName);

        file.transferTo(destination);

        return fileName;
    }
}