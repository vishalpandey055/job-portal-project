package com.jobportal.controller;

import com.jobportal.dto.request.ApplicationRequest;
import com.jobportal.dto.response.ApiResponse;
import com.jobportal.entity.Application;
import com.jobportal.service.ApplicationService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // ===============================
    // Apply Job (Candidate)
    // ===============================
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping("/apply")
    public ApiResponse<Application> apply(
            @Valid @RequestBody ApplicationRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        Application application =
                applicationService.applyJob(request, email);

        return new ApiResponse<>(
                true,
                "Applied successfully",
                application
        );
    }

    // ===============================
    // Candidate Applications
    // ===============================
    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Application>> getUserApplications(@PathVariable Long userId) {

        List<Application> applications =
                applicationService.getApplicationsByUser(userId);

        return new ApiResponse<>(
                true,
                "User applications fetched",
                applications
        );
    }

    // ===============================
    // Recruiter View Applicants
    // ===============================
    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/job/{jobId}")
    public ApiResponse<List<Application>> getJobApplications(@PathVariable Long jobId) {

        List<Application> applications =
                applicationService.getApplicationsByJob(jobId);

        return new ApiResponse<>(
                true,
                "Job applicants fetched",
                applications
        );
    }

    // ===============================
    // Recruiter Update Status
    // ===============================
    @PreAuthorize("hasRole('RECRUITER')")
    @PutMapping("/status/{applicationId}")
    public ApiResponse<Application> updateStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {

        Application application =
                applicationService.updateStatus(applicationId, status);

        return new ApiResponse<>(
                true,
                "Application status updated",
                application
        );
    }
}