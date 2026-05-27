package com.jobportal.job_service.service.impl;

import com.jobportal.job_service.dto.request.CreateJobRequest;
import com.jobportal.job_service.dto.response.JobResponse;
import com.jobportal.job_service.entity.JobEntity;
import com.jobportal.job_service.repository.JobRepository;
import com.jobportal.job_service.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public JobResponse createJob(CreateJobRequest request) {
        log.info("Creating Job: {}", request.getTitle());

        JobEntity job = new JobEntity();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSkill(request.getSkill());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setRecruiterId(request.getRecruiterId());
        job.setRecruiterEmail(request.getRecruiterEmail());
        job.setJobStatus(JobEntity.JobStatus.ACTIVE);

        JobEntity saved = jobRepository.save(job);
        log.info("Job created with Id: {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    @Cacheable(value = "allJobs")
    public List<JobResponse> getAllJobs() {
        log.info("Fetching all active jobs.");
        return jobRepository.findByStatus(JobEntity.JobStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public JobResponse mapToResponse(JobEntity job) {
        return new JobResponse(job.getId(), job.getTitle(), job.getDescription(), job.getCompany(), job.getLocation(),
                job.getSkill(), job.getSalaryMin(), job.getSalaryMax(), job.getRecruiterId(), job.getRecruiterEmail(),
                job.getJobStatus(), job.getCreatedAt());
    }
}
