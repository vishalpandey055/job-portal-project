package com.jobportal.controller;

import com.jobportal.dto.response.AdminAnalyticsResponse;
import com.jobportal.dto.response.ApiResponse;
import com.jobportal.service.AdminService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/analytics")
    public ApiResponse<AdminAnalyticsResponse> getAnalytics() {

        AdminAnalyticsResponse analytics = adminService.getAnalytics();

        return new ApiResponse<>(
                true,
                "Admin analytics fetched successfully",
                analytics
        );
    }
}