package com.jobportal.job_service.service.impl;

import com.jobportal.job_service.dto.request.CreateJobRequest;
import com.jobportal.job_service.dto.response.JobResponse;
import com.jobportal.job_service.entity.JobEntity;
import com.jobportal.job_service.exception.JobNotFoundException;
import com.jobportal.job_service.repository.JobRepository;
import com.jobportal.job_service.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
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
        job.setSkills(request.getSkills());
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
        return jobRepository.findByJobStatus(JobEntity.JobStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "jobs", key = "#id")
    public JobResponse getJobById(Long id) {
        JobEntity job =  jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));
        return mapToResponse(job);
    }

    @Override
    @Cacheable(value = "jobSearch", key = "#keyword")
    public List<JobResponse> searchJobs(String keyword) {
        log.info("Searching job with keyword: {}", keyword);
        return jobRepository.searchJob(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> getJobsByRecruiterId(Long recruiterId) {
        return jobRepository.findByRecruiterId(recruiterId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {"jobs", "allJobs"}, allEntries = true)
    public JobResponse closeJob(Long id) {
        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id : " + id));
        job.setJobStatus(JobEntity.JobStatus.CLOSED);
        return mapToResponse(jobRepository.save(job));
    }

    public JobResponse mapToResponse(JobEntity job) {
        return new JobResponse(job.getId(), job.getTitle(), job.getDescription(), job.getCompany(), job.getLocation(),
                job.getSkills(), job.getSalaryMin(), job.getSalaryMax(), job.getRecruiterId(), job.getRecruiterEmail(),
                job.getJobStatus(), job.getCreatedAt());
    }
}
