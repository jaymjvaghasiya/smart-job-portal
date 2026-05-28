package com.jobportal.job_service.repository;

import com.jobportal.job_service.dto.response.ApplicationResponse;
import com.jobportal.job_service.entity.JobApplication;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<JobApplication, Long> {
    boolean existsByApplicantIdAndJobId(@NotBlank(message = "Application ID is required") Long applicationId, Long jobId);

    List<JobApplication> findByApplicantId(Long applicantId);

    List<JobApplication> findByJobId(Long jobId);
}
