package com.jobportal.controller;

import com.jobportal.dto.request.LoginRequest;
import com.jobportal.dto.request.RefreshTokenRequest;
import com.jobportal.dto.request.RegisterRequest;
import com.jobportal.dto.response.ApiResponse;
import com.jobportal.dto.response.LoginResponse;
import com.jobportal.dto.response.TokenResponse;
import com.jobportal.entity.User;
import com.jobportal.service.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ==============================
    // REGISTER
    // ==============================
    @PostMapping("/register")
    public ApiResponse<User> register(@Valid @RequestBody RegisterRequest request) {

        log.info("Register request received for email: {}", request.getEmail());

        User user = authService.register(request);

        return new ApiResponse<>(
                true,
                "User Registered Successfully",
                user
        );
    }

    // ==============================
    // LOGIN
    // ==============================
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        log.info("Login attempt for email: {}", request.getEmail());

        LoginResponse response = authService.login(request);

        return new ApiResponse<>(
                true,
                "Login successful",
                response
        );
    }

    // ==============================
    // REFRESH TOKEN
    // ==============================
    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(@RequestBody RefreshTokenRequest request) {

        log.info("Refresh token request received");

        String newAccessToken = authService.refreshToken(request.getRefreshToken());

        return new ApiResponse<>(
                true,
                "Token refreshed successfully",
                new TokenResponse(newAccessToken)
        );
    }
}