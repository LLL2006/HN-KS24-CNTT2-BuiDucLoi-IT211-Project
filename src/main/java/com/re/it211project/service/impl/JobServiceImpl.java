package com.re.it211project.service.impl;

import com.re.it211project.dto.request.JobApprovalRequest;
import com.re.it211project.dto.request.JobRequest;
import com.re.it211project.dto.response.JobResponse;
import com.re.it211project.entity.Job;
import com.re.it211project.entity.User;
import com.re.it211project.enums.JobStatus;
import com.re.it211project.exception.BadRequestException;
import com.re.it211project.exception.ForbiddenException;
import com.re.it211project.exception.NotFoundException;
import com.re.it211project.mapper.JobMapper;
import com.re.it211project.repository.JobRepository;
import com.re.it211project.security.CustomUserDetails;
import com.re.it211project.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Override
    public JobResponse createJob(JobRequest request) {
        User employer = getCurrentUser();

        if (employer.getCompany() == null) {
            throw new BadRequestException("Nhà tuyển dụng chưa có thông tin công ty");
        }

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .salary(request.getSalary())
                .deadline(request.getDeadline())
                .status(JobStatus.PENDING)
                .company(employer.getCompany())
                .employer(employer)
                .build();

        return jobMapper.toResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse updateJob(Long id, JobRequest request) {
        User employer = getCurrentUser();

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tin tuyển dụng"));

        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new ForbiddenException("Bạn không có quyền sửa tin này");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setDeadline(request.getDeadline());
        job.setStatus(JobStatus.PENDING);

        return jobMapper.toResponse(jobRepository.save(job));
    }

    @Override
    public void deleteJob(Long id) {
        User employer = getCurrentUser();

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tin tuyển dụng"));

        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new ForbiddenException("Bạn không có quyền xóa tin này");
        }

        jobRepository.delete(job);
    }

    @Override
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tin tuyển dụng"));

        if (job.getStatus() != JobStatus.APPROVED) {
            throw new NotFoundException("Không tìm thấy tin tuyển dụng");
        }

        return jobMapper.toResponse(job);
    }

    @Override
    public Page<JobResponse> getAllApprovedJobs(Pageable pageable) {
        return jobRepository.findByStatus(JobStatus.APPROVED, pageable)
                .map(jobMapper::toResponse);
    }

    @Override
    public Page<JobResponse> getMyJobs(Pageable pageable) {
        User employer = getCurrentUser();

        return jobRepository.findByEmployer(employer, pageable)
                .map(jobMapper::toResponse);
    }

    @Override
    public Page<JobResponse> searchJobs(
            String keyword,
            String location,
            Double minSalary,
            Double maxSalary,
            Pageable pageable
    ) {
        String searchKeyword = keyword == null ? "" : keyword;
        String searchLocation = location == null ? "" : location;
        Double fromSalary = minSalary == null ? 0 : minSalary;
        Double toSalary = maxSalary == null ? Double.MAX_VALUE : maxSalary;

        return jobRepository
                .findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCaseAndSalaryBetweenAndStatus(
                        searchKeyword,
                        searchLocation,
                        fromSalary,
                        toSalary,
                        JobStatus.APPROVED,
                        pageable
                )
                .map(jobMapper::toResponse);
    }

    @Override
    public Page<JobResponse> getPendingJobs(Pageable pageable) {
        return jobRepository.findByStatus(JobStatus.PENDING, pageable)
                .map(jobMapper::toResponse);
    }

    @Override
    public JobResponse approveJob(
            Long id,
            JobApprovalRequest request
    ) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Không tìm thấy tin tuyển dụng"));

        if (job.getStatus() != JobStatus.PENDING) {
            throw new BadRequestException(
                    "Tin tuyển dụng đã được xử lý trước đó"
            );
        }

        job.setStatus(request.getStatus());

        return jobMapper.toResponse(
                jobRepository.save(job)
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