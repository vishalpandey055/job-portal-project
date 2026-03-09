package com.jobportal.repository;

import com.jobportal.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Get notifications ordered by newest first
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

}