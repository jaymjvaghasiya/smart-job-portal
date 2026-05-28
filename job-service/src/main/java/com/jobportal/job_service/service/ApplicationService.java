package com.jobportal.job_service.service;

import com.jobportal.job_service.dto.request.ApplyJobRequest;
import com.jobportal.job_service.dto.response.ApplicationResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicationService {
    ApplicationResponse applyForJob(Long jobId, @Valid ApplyJobRequest request);

    List<ApplicationResponse> getMyApplicationsByApplicant(Long applicantId);

    List<ApplicationResponse> getApplicationsByJob(Long jobId);
}
