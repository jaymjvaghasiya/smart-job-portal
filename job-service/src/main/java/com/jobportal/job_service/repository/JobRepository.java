package com.jobportal.job_service.repository;

import com.jobportal.job_service.dto.response.JobResponse;
import com.jobportal.job_service.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
    List<JobEntity> findByStatus(JobEntity.JobStatus jobStatus);
}
