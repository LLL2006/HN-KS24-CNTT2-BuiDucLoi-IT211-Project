package com.re.it211project.service;

import com.re.it211project.dto.request.LoginRequest;
import com.re.it211project.dto.request.RefreshTokenRequest;
import com.re.it211project.dto.request.RegisterRequest;
import com.re.it211project.dto.response.AuthResponse;
import com.re.it211project.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    void logout(String token);
}