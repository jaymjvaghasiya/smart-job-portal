package com.jobportal.notification_service.service;

import com.jobportal.notification_service.event.JobAppliedEvent;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendApplicationReceivedEmail(JobAppliedEvent event);

    void sendApplicationConfirmationEmail(JobAppliedEvent event);
}
