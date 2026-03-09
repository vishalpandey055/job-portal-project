package com.jobportal.controller;

import com.jobportal.dto.response.ApiResponse;
import com.jobportal.entity.Notification;
import com.jobportal.service.NotificationService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Get user notifications
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Notification>> getUserNotifications(@PathVariable Long userId) {

        List<Notification> notifications =
                notificationService.getUserNotifications(userId);

        return new ApiResponse<>(
                true,
                "Notifications fetched",
                notifications
        );
    }

    // Create notification
    @PostMapping("/create")
    public ApiResponse<Notification> createNotification(
            @RequestParam Long userId,
            @RequestParam String message) {

        Notification notification =
                notificationService.createNotification(userId, message);

        return new ApiResponse<>(
                true,
                "Notification created",
                notification
        );
    }

    // Mark read
    @PutMapping("/read/{notificationId}")
    public ApiResponse<String> markNotificationAsRead(@PathVariable Long notificationId) {

        notificationService.markAsRead(notificationId);

        return new ApiResponse<>(
                true,
                "Notification marked as read",
                null
        );
    }
}