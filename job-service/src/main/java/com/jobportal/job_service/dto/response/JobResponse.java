package com.jobportal.job_service.dto.response;

import com.jobportal.job_service.entity.JobEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String company;

    private String location;

    private String skills;

    private BigDecimal salaryMin;

    private BigDecimal salaryMax;

    private Long recruiterId;

    private String recruiterEmail;

    private JobEntity.JobStatus jobStatus;

    private LocalDateTime postedAt;

}
