package com.jobportal.job_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplyJobRequest {

    @NotNull(message = "Applicant ID is required")
    private Long applicantId;
}
