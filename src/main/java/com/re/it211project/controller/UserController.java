package com.re.it211project.controller;

import com.re.it211project.dto.response.ApiResponse;
import com.re.it211project.dto.response.UserResponse;
import com.re.it211project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser() {
        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Lấy thông tin cá nhân thành công")
                .data(userService.getCurrentUser())
                .build();
    }
}