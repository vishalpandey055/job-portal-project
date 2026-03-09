package com.jobportal.service;

import com.jobportal.entity.Job;
import com.jobportal.entity.SavedJob;
import com.jobportal.entity.User;
import com.jobportal.exception.DuplicateResourceException;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.SavedJobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public SavedJobService(SavedJobRepository savedJobRepository,
                           JobRepository jobRepository,
                           UserRepository userRepository) {
        this.savedJobRepository = savedJobRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public SavedJob saveJob(Long jobId, Long userId) {

        // Prevent duplicate saved jobs
        if (savedJobRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new DuplicateResourceException("Job already saved");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        SavedJob savedJob = SavedJob.builder()
                .job(job)
                .user(user)
                .savedAt(LocalDateTime.now())
                .build();

        return savedJobRepository.save(savedJob);
    }

    public List<SavedJob> getSavedJobs(Long userId) {
        return savedJobRepository.findByUserId(userId);
    }
}