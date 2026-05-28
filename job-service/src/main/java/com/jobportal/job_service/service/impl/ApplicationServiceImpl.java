package com.jobportal.job_service.service.impl;

import com.jobportal.job_service.client.UserClient;
import com.jobportal.job_service.dto.request.ApplyJobRequest;
import com.jobportal.job_service.dto.response.ApplicationResponse;
import com.jobportal.job_service.dto.response.UserResponse;
import com.jobportal.job_service.entity.JobApplication;
import com.jobportal.job_service.entity.JobEntity;
import com.jobportal.job_service.event.JobAppliedEvent;
import com.jobportal.job_service.exception.AlreadyAppliedException;
import com.jobportal.job_service.exception.JobNotFoundException;
import com.jobportal.job_service.kafka.JobEventProducer;
import com.jobportal.job_service.repository.ApplicationRepository;
import com.jobportal.job_service.repository.JobRepository;
import com.jobportal.job_service.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserClient userClient;
    private final JobEventProducer jobEventProducer;

    @Override
    public ApplicationResponse applyForJob(Long jobId, ApplyJobRequest request) {

        log.info("User {} applying for job {}", request.getApplicationId(), jobId);

        JobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + jobId));

        if(job.getJobStatus() == JobEntity.JobStatus.CLOSED) {
            throw new JobNotFoundException("Job is no longer accepting applications");
        }

        if(applicationRepository.existsByApplicantIdAndJobId(request.getApplicationId(), jobId)) {
            throw new AlreadyAppliedException("You have already applied for this job");
        }

        UserResponse user = userClient.getUserById(request.getApplicationId());

        JobApplication application = new JobApplication();
        application.setJobId(jobId);
        application.setApplicantId(request.getApplicationId());
        application.setApplicantName(user.getFirstname() + " " + user.getLastname());
        application.setApplicantEmail(user.getEmail());
        application.setStatus(JobApplication.ApplicationStatus.APPLIED);

        JobApplication saved = applicationRepository.save(application);

        jobEventProducer.publishJobApplied(new JobAppliedEvent(
                jobId,
                job.getTitle(),
                request.getApplicationId(),
                user.getFirstname() + " " + user.getLastname(),
                user.getEmail(),
                job.getRecruiterEmail(),
                LocalDateTime.now()
        ));

        return mapToResponse(saved, job.getTitle());
    }

    @Override
    public List<ApplicationResponse> getMyApplicationsByApplicant(Long applicantId) {
        return applicationRepository.findByApplicantId(applicantId)
                .stream()
                .map(app -> {
                    JobEntity job = jobRepository.findById(app.getJobId()).orElse(null);
                    String title = job != null ? job.getTitle() : "Unknown";
                    return mapToResponse(app, title);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponse> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(app -> {
                    JobEntity job = jobRepository.findById(app.getJobId()).orElse(null);
                    String title = job != null ? job.getTitle() : "Unknown";
                    return mapToResponse(app, title);
                })
                .collect(Collectors.toList());
    }

    private ApplicationResponse mapToResponse(JobApplication application, String jobTitle) {
        return new ApplicationResponse(
                application.getId(),
                application.getJobId(),
                jobTitle,
                application.getApplicantId(),
                application.getApplicantName(),
                application.getStatus(),
                application.getAppliedAt()
        );
    }
}
