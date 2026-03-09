package com.jobportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminAnalyticsResponse {

    private long totalUsers;
    private long totalJobs;
    private long totalApplications;

}