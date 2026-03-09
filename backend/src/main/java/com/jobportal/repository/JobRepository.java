package com.jobportal.repository;

import com.jobportal.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findAll(Pageable pageable);

    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByLocationContainingIgnoreCase(String location);

    List<Job> findBySkillsContainingIgnoreCase(String skill);

    List<Job> findByCompanyId(Long companyId);

    List<Job> findTop10ByOrderByCreatedAtDesc();

    // recruiter jobs
    List<Job> findByRecruiterId(Long recruiterId);
}