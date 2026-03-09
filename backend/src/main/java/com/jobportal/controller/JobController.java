package com.jobportal.controller;

import com.jobportal.dto.request.JobRequest;
import com.jobportal.dto.response.ApiResponse;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.UserRepository;
import com.jobportal.service.JobService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final UserRepository userRepository;

    public JobController(JobService jobService, UserRepository userRepository) {
        this.jobService = jobService;
        this.userRepository = userRepository;
    }

    // ==============================
    // CREATE JOB
    // Recruiter or Admin
    // ==============================
    @PreAuthorize("hasAnyRole('RECRUITER','ADMIN')")
    @PostMapping
    public ApiResponse<Job> createJob(
            @Valid @RequestBody JobRequest request,
            Authentication auth) {

        User recruiter = userRepository
                .findByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Job job = jobService.createJob(request, recruiter);

        return new ApiResponse<>(true, "Job created successfully", job);
    }

    // ==============================
    // GET ALL JOBS
    // All logged-in users
    // ==============================
    @PreAuthorize("hasAnyRole('CANDIDATE','RECRUITER','ADMIN')")
    @GetMapping
    public ApiResponse<Page<Job>> getJobs(Pageable pageable) {

        Page<Job> jobs = jobService.getJobs(pageable);

        return new ApiResponse<>(true, "Jobs fetched successfully", jobs);
    }

    // ==============================
    // GET JOB BY ID
    // ==============================
    @PreAuthorize("hasAnyRole('CANDIDATE','RECRUITER','ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<Job> getJobById(@PathVariable Long id) {

        Job job = jobService.getJobById(id);

        return new ApiResponse<>(true, "Job fetched successfully", job);
    }

    // ==============================
    // UPDATE JOB
    // ==============================
    @PreAuthorize("hasAnyRole('RECRUITER','ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<Job> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request) {

        Job job = jobService.updateJob(id, request);

        return new ApiResponse<>(true, "Job updated successfully", job);
    }

    // ==============================
    // DELETE JOB
    // ==============================
    @PreAuthorize("hasAnyRole('RECRUITER','ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteJob(@PathVariable Long id) {

        jobService.deleteJob(id);

        return new ApiResponse<>(true, "Job deleted successfully", null);
    }

    // ==============================
    // RECRUITER JOBS
    // ==============================
    @PreAuthorize("hasAnyRole('RECRUITER','ADMIN')")
    @GetMapping("/recruiter")
    public ApiResponse<List<Job>> getRecruiterJobs(Authentication auth) {

        User user = userRepository
                .findByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Job> jobs = jobService.getJobsByRecruiter(user.getId());

        return new ApiResponse<>(true, "Recruiter jobs fetched", jobs);
    }

    // ==============================
    // RECOMMENDED JOBS
    // Candidate based recommendation
    // ==============================
    @PreAuthorize("hasAnyRole('CANDIDATE','ADMIN')")
    @GetMapping("/recommend/{userId}")
    public ApiResponse<List<Job>> recommendJobs(@PathVariable Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Job> jobs = jobService.recommendJobs(user.getSkills());

        return new ApiResponse<>(true, "Recommended jobs fetched", jobs);
    }

    // ==============================
    // SEARCH JOBS
    // ==============================
    @PreAuthorize("hasAnyRole('CANDIDATE','RECRUITER','ADMIN')")
    @GetMapping("/search/title")
    public ApiResponse<List<Job>> searchByTitle(@RequestParam String title) {

        List<Job> jobs = jobService.searchByTitle(title);

        return new ApiResponse<>(true, "Jobs found by title", jobs);
    }

    @PreAuthorize("hasAnyRole('CANDIDATE','RECRUITER','ADMIN')")
    @GetMapping("/search/location")
    public ApiResponse<List<Job>> searchByLocation(@RequestParam String location) {

        List<Job> jobs = jobService.searchByLocation(location);

        return new ApiResponse<>(true, "Jobs found by location", jobs);
    }

    @PreAuthorize("hasAnyRole('CANDIDATE','RECRUITER','ADMIN')")
    @GetMapping("/search/skill")
    public ApiResponse<List<Job>> searchBySkill(@RequestParam String skill) {

        List<Job> jobs = jobService.searchBySkill(skill);

        return new ApiResponse<>(true, "Jobs found by skill", jobs);
    }
}