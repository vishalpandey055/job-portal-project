package com.jobportal.service;

import com.jobportal.dto.request.LoginRequest;
import com.jobportal.dto.request.RegisterRequest;
import com.jobportal.dto.response.LoginResponse;
import com.jobportal.entity.Role;
import com.jobportal.entity.User;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.RoleRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.security.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ==============================
    // REGISTER
    // ==============================
    public User register(RegisterRequest request) {

        String email = request.getEmail().toLowerCase();

        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already registered");
        }

        // Convert role to uppercase
        String roleName = request.getRole().toUpperCase();

        // Find role
        Role role = roleRepository
                .findByName(roleName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role " + roleName + " not found in database")
                );

        // Create new user
        User user = new User();

        user.setName(request.getName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setSkills("");
        user.setExperience(0);
        user.setRole(role);

        return userRepository.save(user);
    }

    // ==============================
    // LOGIN
    // ==============================
    public LoginResponse login(LoginRequest request) {

        String email = request.getEmail().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("Invalid email or password");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new LoginResponse(
                accessToken,
                refreshToken,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName()
        );
    }

    // ==============================
    // REFRESH TOKEN
    // ==============================
    public String refreshToken(String refreshToken) {

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new IllegalStateException("Invalid refresh token");
        }

        String email = jwtUtil.extractEmail(refreshToken);

        return jwtUtil.generateAccessToken(email);
    }
}