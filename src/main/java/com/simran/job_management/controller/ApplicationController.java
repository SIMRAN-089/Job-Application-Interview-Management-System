
package com.simran.job_management.controller;

import com.simran.job_management.dto.ApplicationDTO;
import com.simran.job_management.entity.ApplicationStatus;
import com.simran.job_management.service.ApplicationService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // Apply for a job using the authenticated candidate's identity
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationDTO applyForJob(
            @RequestBody ApplicationDTO dto,
            Authentication authentication) {

        return applicationService.applyForJob(
                dto.getJobId(),
                authentication.getName()
        );
    }

    // Update application status
    @PutMapping("/{applicationId}/status")
    public ApplicationDTO updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody StatusUpdateRequest request) {

        return applicationService.updateApplicationStatus(
                applicationId,
                request.getStatus()
        );
    }

    // Get all applications for a job
    @GetMapping("/job/{jobId}")
    public List<ApplicationDTO> getApplicationsByJob(
            @PathVariable Long jobId) {

        return applicationService.getApplicationsByJob(jobId);
    }

    // Filter applications for a job by status
    @GetMapping("/job/{jobId}/status")
    public List<ApplicationDTO> getApplicationsByJobAndStatus(
            @PathVariable Long jobId,
            @RequestParam ApplicationStatus status) {

        return applicationService.getApplicationsByJobAndStatus(
                jobId,
                status
        );
    }

    // Request body for status updates
    public static class StatusUpdateRequest {

        private ApplicationStatus status;

        public ApplicationStatus getStatus() {
            return status;
        }

        public void setStatus(ApplicationStatus status) {
            this.status = status;
        }
    }
}
