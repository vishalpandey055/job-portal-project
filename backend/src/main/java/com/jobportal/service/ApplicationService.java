package com.jobportal.service;

import com.jobportal.dto.request.ApplicationRequest;
import com.jobportal.entity.*;
import com.jobportal.exception.DuplicateResourceException;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.utils.SkillMatcher;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              JobRepository jobRepository,
                              UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    // Apply job
    public Application applyJob(ApplicationRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (applicationRepository.existsByUserIdAndJobId(user.getId(), job.getId())) {
            throw new DuplicateResourceException("You already applied for this job");
        }

        String jobSkills = job.getSkills() == null ? "" : job.getSkills();
        String userSkills = user.getSkills() == null ? "" : user.getSkills();

        int matchScore = SkillMatcher.calculateMatch(jobSkills, userSkills);

        Application application = Application.builder()
                .job(job)
                .user(user)
                .resumeUrl(request.getResumeUrl())
                .status(ApplicationStatus.APPLIED)
                .matchScore(matchScore)
                .appliedAt(LocalDateTime.now())
                .build();

        return applicationRepository.save(application);
    }

    // Recruiter view applicants
    public List<Application> getApplicationsByJob(Long jobId) {

        jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        return applicationRepository.findByJobIdOrderByMatchScoreDesc(jobId);
    }

    // Candidate view own applications
    public List<Application> getApplicationsByUser(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return applicationRepository.findByUserId(userId);
    }

    // Recruiter update status
    public Application updateStatus(Long applicationId, String status) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        ApplicationStatus newStatus;

        try {
            newStatus = ApplicationStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Status must be SHORTLISTED or REJECTED");
        }

        application.setStatus(newStatus);

        return applicationRepository.save(application);
    }
}