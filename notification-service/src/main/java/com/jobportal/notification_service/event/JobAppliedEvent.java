package com.jobportal.notification_service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobAppliedEvent {
    private Long jobId;
    private String jobTitle;
    private Long applicantId;
    private String applicantName;
    private String applicantEmail;
    private String recruiterEmail;
    private LocalDateTime appliedAt;
}
