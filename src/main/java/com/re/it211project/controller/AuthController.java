package com.re.it211project.controller;

import com.re.it211project.dto.request.LoginRequest;
import com.re.it211project.dto.request.RefreshTokenRequest;
import com.re.it211project.dto.request.RegisterRequest;
import com.re.it211project.dto.response.ApiResponse;
import com.re.it211project.dto.response.AuthResponse;
import com.re.it211project.dto.response.RegisterResponse;
import com.re.it211project.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<RegisterResponse>builder()
                                .success(true)
                                .message("Đăng ký thành công")
                                .data(authService.register(request))
                                .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Đăng nhập thành công")
                        .data(authService.login(request))
                        .build()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Refresh token thành công")
                        .data(authService.refreshToken(request))
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader("Authorization") String authorization
    ) {
        String token = authorization.substring(7);
        authService.logout(token);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Đăng xuất thành công")
                        .data(null)
                        .build()
        );
    }
}