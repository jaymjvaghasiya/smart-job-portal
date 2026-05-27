package com.jobportal.job_service.repository;

import com.jobportal.job_service.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<JobApplication, Long> {
}
