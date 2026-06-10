package com.re.it211project.controller;

import com.re.it211project.dto.request.JobRequest;
import com.re.it211project.dto.response.ApiResponse;
import com.re.it211project.dto.response.JobResponse;
import com.re.it211project.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ApiResponse<Page<JobResponse>> getAllApprovedJobs(Pageable pageable) {
        return ApiResponse.<Page<JobResponse>>builder()
                .success(true)
                .message("Lấy danh sách việc làm thành công")
                .data(jobService.getAllApprovedJobs(pageable))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<Page<JobResponse>> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary,
            Pageable pageable
    ) {
        return ApiResponse.<Page<JobResponse>>builder()
                .success(true)
                .message("Tìm kiếm việc làm thành công")
                .data(jobService.searchJobs(keyword, location, minSalary, maxSalary, pageable))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<JobResponse> getJobById(@PathVariable Long id) {
        return ApiResponse.<JobResponse>builder()
                .success(true)
                .message("Lấy chi tiết việc làm thành công")
                .data(jobService.getJobById(id))
                .build();
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping
    public ApiResponse<JobResponse> createJob(
            @Valid @RequestBody JobRequest request
    ) {
        return ApiResponse.<JobResponse>builder()
                .success(true)
                .message("Tạo tin tuyển dụng thành công")
                .data(jobService.createJob(request))
                .build();
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/my-jobs")
    public ApiResponse<Page<JobResponse>> getMyJobs(Pageable pageable) {
        return ApiResponse.<Page<JobResponse>>builder()
                .success(true)
                .message("Lấy tin tuyển dụng của tôi thành công")
                .data(jobService.getMyJobs(pageable))
                .build();
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{id}")
    public ApiResponse<JobResponse> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request
    ) {
        return ApiResponse.<JobResponse>builder()
                .success(true)
                .message("Cập nhật tin tuyển dụng thành công")
                .data(jobService.updateJob(id, request))
                .build();
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa tin tuyển dụng thành công")
                .data(null)
                .build();
    }
}