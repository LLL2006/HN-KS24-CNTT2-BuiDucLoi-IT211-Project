package com.re.it211project.controller;

import com.re.it211project.dto.request.JobApprovalRequest;
import com.re.it211project.dto.response.ApiResponse;
import com.re.it211project.dto.response.JobResponse;
import com.re.it211project.dto.response.UserResponse;
import com.re.it211project.enums.UserStatus;
import com.re.it211project.service.JobService;
import com.re.it211project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final JobService jobService;

    @GetMapping("/users")
    public ApiResponse<Page<UserResponse>> getAllUsers(Pageable pageable) {
        return ApiResponse.<Page<UserResponse>>builder()
                .success(true)
                .message("Admin lấy danh sách user thành công")
                .data(userService.getAllUsers(pageable))
                .build();
    }

    @PatchMapping("/users/{id}/status")
    public ApiResponse<UserResponse> updateUserStatus(
            @PathVariable Long id,
            @RequestParam UserStatus status
    ) {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Cập nhật trạng thái user thành công")
                .data(userService.updateUserStatus(id, status))
                .build();
    }

    @DeleteMapping("/users/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Vô hiệu hóa user thành công")
                .data(null)
                .build();
    }

    @GetMapping("/jobs/pending")
    public ApiResponse<Page<JobResponse>> getPendingJobs(Pageable pageable) {
        return ApiResponse.<Page<JobResponse>>builder()
                .success(true)
                .message("Lấy danh sách tin chờ duyệt thành công")
                .data(jobService.getPendingJobs(pageable))
                .build();
    }

    @PatchMapping("/jobs/{id}/approve")
    public ApiResponse<JobResponse> approveJob(
            @PathVariable Long id,
            @Valid @RequestBody JobApprovalRequest request
    ) {
        return ApiResponse.<JobResponse>builder()
                .success(true)
                .message("Duyệt tin tuyển dụng thành công")
                .data(jobService.approveJob(id, request))
                .build();
    }
}