package com.jobportal.dto.response;

import com.jobportal.entity.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;
    private String jobTitle;
    private String company;
    private ApplicationStatus status;
    private int matchScore;
}