package com.re.it211project.controller;

import com.re.it211project.dto.request.JobApplicationRequest;
import com.re.it211project.dto.request.UpdateApplicationStatusRequest;
import com.re.it211project.dto.response.ApiResponse;
import com.re.it211project.dto.response.JobApplicationResponse;
import com.re.it211project.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping
    public ApiResponse<JobApplicationResponse> applyJob(
            @Valid @RequestBody JobApplicationRequest request
    ) {
        return ApiResponse.<JobApplicationResponse>builder()
                .success(true)
                .message("Ứng tuyển thành công")
                .data(jobApplicationService.applyJob(request))
                .build();
    }

    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/my-applications")
    public ApiResponse<Page<JobApplicationResponse>> getMyApplications(
            Pageable pageable
    ) {
        return ApiResponse.<Page<JobApplicationResponse>>builder()
                .success(true)
                .message("Lấy danh sách hồ sơ của tôi thành công")
                .data(jobApplicationService.getMyApplications(pageable))
                .build();
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/job/{jobId}")
    public ApiResponse<Page<JobApplicationResponse>> getApplicationsByJob(
            @PathVariable Long jobId,
            Pageable pageable
    ) {
        return ApiResponse.<Page<JobApplicationResponse>>builder()
                .success(true)
                .message("Lấy danh sách ứng viên theo tin tuyển dụng thành công")
                .data(jobApplicationService.getApplicationsByJob(jobId, pageable))
                .build();
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PatchMapping("/{id}/status")
    public ApiResponse<JobApplicationResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateApplicationStatusRequest request
    ) {
        return ApiResponse.<JobApplicationResponse>builder()
                .success(true)
                .message("Cập nhật trạng thái hồ sơ thành công")
                .data(jobApplicationService.updateStatus(id, request))
                .build();
    }
}