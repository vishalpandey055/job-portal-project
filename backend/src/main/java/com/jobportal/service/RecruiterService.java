package com.jobportal.service;

import com.jobportal.dto.response.RecruiterDashboardResponse;
import com.jobportal.entity.User;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class RecruiterService {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public RecruiterService(JobRepository jobRepository,
                            ApplicationRepository applicationRepository,
                            UserRepository userRepository) {

        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    public RecruiterDashboardResponse getDashboard(String email) {

        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found"));

        long totalJobs = jobRepository.findByCompanyId(recruiter.getId()).size();
        long totalApplications = applicationRepository.count();
        long shortlisted = applicationRepository.findAll().stream()
                .filter(a -> a.getStatus().name().equals("SHORTLISTED"))
                .count();

        return new RecruiterDashboardResponse(
                totalJobs,
                totalApplications,
                shortlisted
        );
    }
}