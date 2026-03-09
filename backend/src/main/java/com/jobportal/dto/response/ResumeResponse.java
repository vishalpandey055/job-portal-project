package com.jobportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResumeResponse {

    private Long id;
    private String fileName;
    private String fileUrl;
}