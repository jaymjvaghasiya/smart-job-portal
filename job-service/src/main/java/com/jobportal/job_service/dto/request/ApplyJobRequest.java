package com.jobportal.job_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplyJobRequest {

    @NotBlank(message = "Application ID is required")
    private Long applicationId;
}
