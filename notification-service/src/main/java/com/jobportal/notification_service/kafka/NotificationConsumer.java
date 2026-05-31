package com.jobportal.notification_service.kafka;

import com.jobportal.notification_service.event.JobAppliedEvent;
import com.jobportal.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final EmailService emailService;

    @KafkaListener(
            topics = "job-applied-event",
            groupId = "notification-group"
    )
    public void handleJobApplied(JobAppliedEvent event) {
        log.info("Receiving job applied event: JobId={} applicant={}", event.getJobId(), event.getApplicantEmail());
        emailService.sendApplicationReceivedEmail(event);
        emailService.sendApplicationConfirmationEmail(event);
        log.info("Notification sent for jobId: {}", event.getJobId());
    }
}
