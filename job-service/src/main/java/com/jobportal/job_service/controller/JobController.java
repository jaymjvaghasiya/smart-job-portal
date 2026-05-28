package com.jobportal.job_service.controller;

import com.jobportal.job_service.dto.request.CreateJobRequest;
import com.jobportal.job_service.dto.response.JobResponse;
import com.jobportal.job_service.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody CreateJobRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(request));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok().body(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok().body(jobService.getJobById(id));
    }

    @GetMapping("/{search}")
    public ResponseEntity<List<JobResponse>> searchJobs(@RequestParam String keyword) {
        return ResponseEntity.ok().body(jobService.searchJobs(keyword));
    }

    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<JobResponse>> getJobsByRecruiter(@PathVariable Long recruiterId) {
        return ResponseEntity.ok().body(jobService.getJobsByRecruiterId(recruiterId));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<JobResponse> closeJob(@PathVariable Long id) {
        return ResponseEntity.ok().body(jobService.closeJob(id));
    }
}
