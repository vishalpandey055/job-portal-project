package com.jobportal.service;

import com.jobportal.entity.Resume;
import com.jobportal.entity.User;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.ResumeRepository;
import com.jobportal.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeService(ResumeRepository resumeRepository,
                         UserRepository userRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    public Resume uploadResume(Long userId, String fileName, String fileUrl) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Resume resume = Resume.builder()
                .user(user)
                .fileName(fileName)
                .fileUrl(fileUrl)
                .uploadedAt(LocalDateTime.now())
                .build();

        return resumeRepository.save(resume);
    }

    public List<Resume> getUserResumes(Long userId) {
        return resumeRepository.findByUserId(userId);
    }
}