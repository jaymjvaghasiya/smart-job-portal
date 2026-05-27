package com.jobportal.job_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateJobRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Company is required")
    private String company;

    @NotBlank(message = "Location is required.")
    private String location;

    private List<String> skill;

    private Double salaryMin;

    private Double salaryMax;

    @NotBlank(message = "Recruiter ID is required")
    private Long recruiterId;

    @NotBlank(message = "Recruiter email is required")
    private String recruiterEmail;
}
