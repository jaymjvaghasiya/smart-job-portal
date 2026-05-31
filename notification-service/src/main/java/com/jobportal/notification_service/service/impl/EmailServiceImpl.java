package com.jobportal.notification_service.service.impl;

import com.jobportal.notification_service.event.JobAppliedEvent;
import com.jobportal.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendApplicationReceivedEmail(JobAppliedEvent event) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(event.getRecruiterEmail());
            mail.setSubject("New Application: " + event.getJobTitle());
            mail.setText(
                    """
                        Hello, 
                        
                        You have received a nre application for your job posting.
                        
                        Job Title:      %s
                        Applicant:      %s
                        Email:          %s
                        Applied at:     %d
                        
                        Login to Smart Job Portal to review the application.
                        
                        Regards,
                        Smart Job Portal Team
                    """.formatted(
                            event.getJobTitle(),
                            event.getApplicantName(),
                            event.getApplicantEmail(),
                            event.getAppliedAt()
                    )
            );

            mailSender.send(mail);

            log.info("Email send to recruiter: {}", event.getRecruiterEmail());
        } catch(Exception e) {
            log.error("Failed to send an email to recruiter: {}", e.getMessage());
        }
    }

    @Override
    public void sendApplicationConfirmationEmail(JobAppliedEvent event) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(event.getApplicantEmail());
            mail.setSubject("Application Submitted: " + event.getJobTitle());
            mail.setText("""
                        Hello %s
                        
                        Your application has been submitted successfully!
                        
                        Job Title:      %s
                        Applied At:     %s
                        
                        We will notify you when the recruiter reviews your application.
                        
                        Regards,
                        Smart Job Portal Team 
                    """.formatted(
                            event.getApplicantName(),
                            event.getJobTitle(),
                            event.getAppliedAt()
                    )
            );

            mailSender.send(mail);
            log.info("Confirmation email sent to applicant: {}", event.getApplicantEmail());
        } catch (Exception e) {
            log.error("Failed to send confirmation email : {}", e.getMessage());
        }
    }
}
