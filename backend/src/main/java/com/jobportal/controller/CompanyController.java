package com.jobportal.controller;

import com.jobportal.dto.response.ApiResponse;
import com.jobportal.entity.Company;
import com.jobportal.service.CompanyService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // Recruiter or Admin can create company
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECRUITER')")
    @PostMapping
    public ApiResponse<Company> createCompany(@Valid @RequestBody Company company) {

        Company created = companyService.createCompany(company);

        return new ApiResponse<>(
                true,
                "Company created successfully",
                created
        );
    }

    @GetMapping
    public ApiResponse<List<Company>> getCompanies() {

        List<Company> companies = companyService.getCompanies();

        return new ApiResponse<>(
                true,
                "Companies fetched successfully",
                companies
        );
    }
}