package com.re.it211project.controller;

import com.re.it211project.dto.request.ChangePasswordRequest;
import com.re.it211project.dto.request.ForgotPasswordRequest;
import com.re.it211project.dto.request.ResetPasswordRequest;
import com.re.it211project.dto.response.ApiResponse;
import com.re.it211project.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/change")
    public ApiResponse<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        passwordResetService.changePassword(request);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Đổi mật khẩu thành công")
                .data(null)
                .build();
    }

    @PostMapping("/forgot")
    public ApiResponse<String> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {
        String token = passwordResetService.forgotPassword(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Token đặt lại mật khẩu đã được tạo")
                .data(token)
                .build();
    }

    @PostMapping("/reset")
    public ApiResponse<Void> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        passwordResetService.resetPassword(request);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Đặt lại mật khẩu thành công")
                .data(null)
                .build();
    }
}