package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String location;

    private String skills;

    private double salary;

    private LocalDateTime createdAt;

    // Recruiter who created the job
    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private User recruiter;
}