package com.re.it211project.service;

import com.re.it211project.dto.request.JobApprovalRequest;
import com.re.it211project.dto.request.JobRequest;
import com.re.it211project.dto.response.JobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {

    JobResponse createJob(JobRequest request);

    JobResponse updateJob(Long id, JobRequest request);

    void deleteJob(Long id);

    JobResponse getJobById(Long id);

    Page<JobResponse> getAllApprovedJobs(Pageable pageable);

    Page<JobResponse> getMyJobs(Pageable pageable);

    Page<JobResponse> searchJobs(
            String keyword,
            String location,
            Double minSalary,
            Double maxSalary,
            Pageable pageable
    );

    Page<JobResponse> getPendingJobs(Pageable pageable);

    JobResponse approveJob(Long id, JobApprovalRequest request);
}