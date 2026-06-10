package com.re.it211project.service;

import com.re.it211project.dto.request.JobApplicationRequest;
import com.re.it211project.dto.request.UpdateApplicationStatusRequest;
import com.re.it211project.dto.response.JobApplicationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobApplicationService {

    JobApplicationResponse applyJob(JobApplicationRequest request);

    Page<JobApplicationResponse> getMyApplications(Pageable pageable);

    Page<JobApplicationResponse> getApplicationsByJob(Long jobId, Pageable pageable);

    JobApplicationResponse updateStatus(Long id, UpdateApplicationStatusRequest request);
}