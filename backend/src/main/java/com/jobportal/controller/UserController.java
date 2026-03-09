package com.jobportal.controller;

import com.jobportal.service.UserService;
import com.jobportal.dto.response.ApiResponse;
import com.jobportal.entity.User;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Only ADMIN can see all users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<User>> getUsers() {

        List<User> users = userService.getUsers();

        return new ApiResponse<>(
                true,
                "Users fetched successfully",
                users
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<User> getUser(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return new ApiResponse<>(
                true,
                "User fetched successfully",
                user
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User user) {

        User updatedUser = userService.updateUser(id, user);

        return new ApiResponse<>(
                true,
                "User updated successfully",
                updatedUser
        );
    }
}