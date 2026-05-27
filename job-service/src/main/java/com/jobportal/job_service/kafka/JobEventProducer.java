package com.jobportal.job_service.kafka;

import com.jobportal.job_service.event.JobAppliedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobEventProducer {

    private final KafkaTemplate<String, JobAppliedEvent> kafkaTemplate;
    private static final String TOPIC = "job-applied-event";

    public void publishJobApplied(JobAppliedEvent event) {
        log.info("Publishing job applied event for job id : {}", event.getJobId());
        kafkaTemplate.send(TOPIC, String.valueOf(event.getJobId()), event)
                .whenComplete((result, ex) -> {
                    if(ex == null) {
                        log.info("Event published successfully: JobId={}", event.getJobId());
                    } else {
                        log.error("Failed to publish event jobId={}", event.getJobId());
                    }
                });
    }
}
