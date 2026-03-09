package com.jobportal.controller;

import com.jobportal.dto.response.ApiResponse;
import com.jobportal.service.FileStorageService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload-resume")
    public ApiResponse<String> uploadResume(@RequestParam("file") MultipartFile file) throws IOException {

        String fileName = fileStorageService.saveFile(file);

        return new ApiResponse<>(
                true,
                "File uploaded successfully",
                fileName
        );
    }
}