package com.jobportal.job_service.repository;

import com.jobportal.job_service.entity.JobEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {
    List<JobEntity> findByJobStatus(JobEntity.JobStatus jobStatus);

    @Query("""
            SELECT j FROM JobEntity j WHERE j.jobStatus = 'ACTIVE' AND 
            (LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(j.location) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    List<JobEntity> searchJob(@Param("keyword") String keyword);

    List<JobEntity> findByRecruiterId(Long recruiterId);
}
