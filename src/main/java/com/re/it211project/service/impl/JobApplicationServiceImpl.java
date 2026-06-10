package com.re.it211project.service.impl;

import com.re.it211project.dto.request.JobApplicationRequest;
import com.re.it211project.dto.request.UpdateApplicationStatusRequest;
import com.re.it211project.dto.response.JobApplicationResponse;
import com.re.it211project.entity.Job;
import com.re.it211project.entity.JobApplication;
import com.re.it211project.entity.User;
import com.re.it211project.enums.ApplicationStatus;
import com.re.it211project.enums.JobStatus;
import com.re.it211project.exception.BadRequestException;
import com.re.it211project.exception.ConflictException;
import com.re.it211project.exception.ForbiddenException;
import com.re.it211project.exception.NotFoundException;
import com.re.it211project.mapper.JobApplicationMapper;
import com.re.it211project.repository.JobApplicationRepository;
import com.re.it211project.repository.JobRepository;
import com.re.it211project.security.CustomUserDetails;
import com.re.it211project.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;
    private final JobApplicationMapper jobApplicationMapper;

    @Override
    public JobApplicationResponse applyJob(JobApplicationRequest request) {
        User candidate = getCurrentUser();

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tin tuyển dụng"));

        if (job.getStatus() != JobStatus.APPROVED) {
            throw new BadRequestException("Tin tuyển dụng chưa mở ứng tuyển");
        }

        if (candidate.getCvUrl() == null || candidate.getCvUrl().isBlank()) {
            throw new BadRequestException("Bạn cần upload CV trước khi ứng tuyển");
        }

        if (jobApplicationRepository.existsByJobAndCandidate(job, candidate)) {
            throw new ConflictException("Bạn đã ứng tuyển công việc này rồi");
        }

        JobApplication application = JobApplication.builder()
                .job(job)
                .candidate(candidate)
                .coverLetter(request.getCoverLetter())
                .status(ApplicationStatus.PENDING)
                .appliedAt(LocalDateTime.now())
                .build();

        return jobApplicationMapper.toResponse(
                jobApplicationRepository.save(application)
        );
    }

    @Override
    public Page<JobApplicationResponse> getMyApplications(Pageable pageable) {
        User candidate = getCurrentUser();

        return jobApplicationRepository.findByCandidate(candidate, pageable)
                .map(jobApplicationMapper::toResponse);
    }

    @Override
    public Page<JobApplicationResponse> getApplicationsByJob(Long jobId, Pageable pageable) {
        User employer = getCurrentUser();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tin tuyển dụng"));

        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new ForbiddenException("Bạn không có quyền xem hồ sơ của tin này");
        }

        return jobApplicationRepository.findByJob(job, pageable)
                .map(jobApplicationMapper::toResponse);
    }

    @Override
    public JobApplicationResponse updateStatus(
            Long id,
            UpdateApplicationStatusRequest request
    ) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Không tìm thấy hồ sơ ứng tuyển"));

        if (application.getStatus() == ApplicationStatus.ACCEPTED
                || application.getStatus() == ApplicationStatus.REJECTED) {
            throw new BadRequestException("Hồ sơ đã được xử lý trước đó");
        }

        application.setStatus(request.getStatus());

        return jobApplicationMapper.toResponse(
                jobApplicationRepository.save(application)
        );
    }

    private User getCurrentUser() {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userDetails.getUser();
    }
}