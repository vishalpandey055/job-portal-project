package com.jobportal.controller;

import com.jobportal.dto.response.ApiResponse;
import com.jobportal.entity.Resume;
import com.jobportal.service.ResumeService;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    // Only candidates upload resumes
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/upload")
    public ApiResponse<Resume> uploadResume(
            @RequestParam @NotNull Long userId,
            @RequestParam @NotBlank String fileName,
            @RequestParam @NotBlank String fileUrl) {

        Resume resume =
                resumeService.uploadResume(userId, fileName, fileUrl);

        return new ApiResponse<>(
                true,
                "Resume uploaded successfully",
                resume
        );
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Resume>> getUserResumes(
            @PathVariable Long userId) {

        List<Resume> resumes = resumeService.getUserResumes(userId);

        return new ApiResponse<>(
                true,
                "User resumes fetched",
                resumes
        );
    }
}