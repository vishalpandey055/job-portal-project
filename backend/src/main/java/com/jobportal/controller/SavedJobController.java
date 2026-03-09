package com.jobportal.controller;

import com.jobportal.dto.request.SaveJobRequest;
import com.jobportal.dto.response.ApiResponse;
import com.jobportal.entity.SavedJob;
import com.jobportal.service.SavedJobService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-jobs")
public class SavedJobController {

    private final SavedJobService savedJobService;

    public SavedJobController(SavedJobService savedJobService) {
        this.savedJobService = savedJobService;
    }

    // Only candidates can save jobs
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping
    public ApiResponse<SavedJob> saveJob(
            @Valid @RequestBody SaveJobRequest request) {

        SavedJob savedJob =
                savedJobService.saveJob(request.getJobId(), request.getUserId());

        return new ApiResponse<>(
                true,
                "Job saved successfully",
                savedJob
        );
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/user/{userId}")
    public ApiResponse<List<SavedJob>> getSavedJobs(
            @PathVariable Long userId) {

        List<SavedJob> jobs =
                savedJobService.getSavedJobs(userId);

        return new ApiResponse<>(
                true,
                "Saved jobs fetched",
                jobs
        );
    }
}