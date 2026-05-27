package com.jobportal.job_service.service;

import com.jobportal.job_service.dto.request.CreateJobRequest;
import com.jobportal.job_service.dto.response.JobResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface JobService {
    JobResponse createJob(@Valid CreateJobRequest request);
}
