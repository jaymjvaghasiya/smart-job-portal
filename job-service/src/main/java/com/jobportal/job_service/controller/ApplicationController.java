package com.jobportal.job_service.controller;

import com.jobportal.job_service.dto.request.ApplyJobRequest;
import com.jobportal.job_service.dto.response.ApplicationResponse;
import com.jobportal.job_service.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/{jobId}/apply")
    public ResponseEntity<ApplicationResponse> applyForJob(@PathVariable Long jobId,
                                                           @Valid @RequestBody ApplyJobRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.applyForJob(jobId, request));
    }

    @GetMapping("/applications/applicant/{applicantId}")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(@PathVariable Long applicantId) {
        return ResponseEntity.ok().body(applicationService.getMyApplicationsByApplicant(applicantId));
    }

    @GetMapping("/{jobId}/applications")
    public ResponseEntity<List<ApplicationResponse>> getJobApplications(@PathVariable Long jobId) {
        return ResponseEntity.ok().body(applicationService.getApplicationsByJob(jobId));
    }
}
