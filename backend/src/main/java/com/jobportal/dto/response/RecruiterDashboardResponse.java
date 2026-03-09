package com.jobportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecruiterDashboardResponse {

    private long totalJobs;
    private long totalApplications;
    private long shortlisted;

}