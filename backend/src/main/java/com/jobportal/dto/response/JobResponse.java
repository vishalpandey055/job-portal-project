package com.jobportal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobResponse {

    private Long id;
    private String title;
    private String company;
    private String location;
    private String skills;
    private double salary;
}