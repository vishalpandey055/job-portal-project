package com.jobportal.service;

import com.jobportal.dto.request.JobRequest;
import com.jobportal.entity.Company;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.CompanyRepository;
import com.jobportal.repository.JobRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository,
                      CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    // Create Job
    public Job createJob(JobRequest request, User recruiter) {

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .company(company)
                .location(request.getLocation())
                .skills(request.getSkills())
                .salary(request.getSalary())
                .createdAt(LocalDateTime.now())
                .recruiter(recruiter)
                .build();

        return jobRepository.save(job);
    }

    public Page<Job> getJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    public Job getJobById(Long id) {

        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found with id: " + id));
    }

    public List<Job> getJobsByRecruiter(Long recruiterId) {
        return jobRepository.findByRecruiterId(recruiterId);
    }

    public Job updateJob(Long id, JobRequest request) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found with id: " + id));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Company not found"));

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(company);
        job.setLocation(request.getLocation());
        job.setSkills(request.getSkills());
        job.setSalary(request.getSalary());

        return jobRepository.save(job);
    }

    public void deleteJob(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found with id: " + id));

        jobRepository.delete(job);
    }

    public List<Job> recommendJobs(String userSkills) {

        if (userSkills == null || userSkills.isBlank()) {
            return jobRepository.findTop10ByOrderByCreatedAtDesc();
        }

        String[] skills = userSkills.split(",");

        Set<Job> recommendedJobs = new HashSet<>();

        for (String skill : skills) {

            if (skill == null || skill.isBlank()) continue;

            List<Job> jobs =
                    jobRepository.findBySkillsContainingIgnoreCase(skill.trim());

            recommendedJobs.addAll(jobs);
        }

        recommendedJobs.addAll(jobRepository.findTop10ByOrderByCreatedAtDesc());

        return new ArrayList<>(recommendedJobs);
    }

    public List<Job> searchByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Job> searchByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Job> searchBySkill(String skill) {
        return jobRepository.findBySkillsContainingIgnoreCase(skill);
    }
}