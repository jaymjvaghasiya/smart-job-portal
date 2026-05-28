package com.jobportal.job_service.service;

import com.jobportal.job_service.dto.request.CreateJobRequest;
import com.jobportal.job_service.dto.response.JobResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {
    JobResponse createJob(@Valid CreateJobRequest request);

    List<JobResponse> getAllJobs();

    JobResponse getJobById(Long id);

    List<JobResponse> searchJobs(String keyword);

    List<JobResponse> getJobsByRecruiterId(Long recruiterId);

    JobResponse closeJob(Long id);
}
