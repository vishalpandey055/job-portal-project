package com.jobportal.repository;

import com.jobportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByJobId(Long jobId);

    List<Application> findByUserId(Long userId);

    List<Application> findByJobIdOrderByMatchScoreDesc(Long jobId);

    boolean existsByUserIdAndJobId(Long userId, Long jobId);
}