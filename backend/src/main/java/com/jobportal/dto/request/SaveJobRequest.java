package com.jobportal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaveJobRequest {

    @NotNull(message = "Job ID is required")
    private Long jobId;

    @NotNull(message = "User ID is required")
    private Long userId;
}