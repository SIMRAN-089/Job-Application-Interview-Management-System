
package com.simran.job_management.service;

import com.simran.job_management.dto.JobDTO;
import com.simran.job_management.entity.Job;
import com.simran.job_management.entity.User;
import com.simran.job_management.mapper.JobMapper;
import com.simran.job_management.repository.JobRepository;
import com.simran.job_management.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository,
                      UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    // Get all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get jobs with pagination
    public Page<Job> getJobsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobRepository.findAll(pageable);
    }

    // Search jobs by title and location
    public List<Job> searchJobs(String title, String location) {
        return jobRepository
                .findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(
                        title, location);
    }

    // Filter jobs by minimum salary
    public List<Job> filterJobsBySalary(int minSalary) {
        return jobRepository
                .findBySalaryGreaterThanEqual(minSalary);
    }

    // Filter jobs by location
    public List<Job> filterJobsByLocation(String location) {
        return jobRepository
                .findByLocationContainingIgnoreCase(location);
    }

    // Get job by ID
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    // Create a job
    public JobDTO createJob(JobDTO dto) {
        User recruiter = userRepository.findById(dto.getRecruiterId())
                .orElseThrow(() ->
                        new RuntimeException("Recruiter not found"));

        Job job = JobMapper.toEntity(dto);
        job.setRecruiter(recruiter);

        Job savedJob = jobRepository.save(job);

        return JobMapper.toDTO(savedJob);
    }

    // Update a job
    public Job updateJob(Long id, JobDTO dto) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        job.setTitle(dto.getTitle());
        job.setCompany(dto.getCompany());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setSalary(dto.getSalary());

        return jobRepository.save(job);
    }

    // Delete a job
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
