package com.jobportal.controller;

import com.jobportal.dto.response.ApiResponse;
import com.jobportal.dto.response.RecruiterDashboardResponse;
import com.jobportal.service.RecruiterService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {

    private final RecruiterService recruiterService;

    public RecruiterController(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/dashboard")
    public ApiResponse<RecruiterDashboardResponse> dashboard(
            Authentication authentication) {

        String email = authentication.getName();

        RecruiterDashboardResponse response =
                recruiterService.getDashboard(email);

        return new ApiResponse<>(
                true,
                "Recruiter dashboard fetched",
                response
        );
    }
}