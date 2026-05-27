package com.jobportal.job_service.dto.response;

import com.jobportal.job_service.entity.JobApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private Long applicantId;

    private String applicantName;

    private JobApplication.ApplicationStatus status;

    private LocalDateTime appliedAt;
}
