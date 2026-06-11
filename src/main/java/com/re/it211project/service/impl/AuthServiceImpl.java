package com.re.it211project.service.impl;

import com.re.it211project.dto.request.LoginRequest;
import com.re.it211project.dto.request.RefreshTokenRequest;
import com.re.it211project.dto.request.RegisterRequest;
import com.re.it211project.dto.response.AuthResponse;
import com.re.it211project.entity.Company;
import com.re.it211project.entity.Role;
import com.re.it211project.entity.User;
import com.re.it211project.enums.RoleName;
import com.re.it211project.enums.UserStatus;
import com.re.it211project.exception.ConflictException;
import com.re.it211project.exception.ForbiddenException;
import com.re.it211project.exception.NotFoundException;
import com.re.it211project.mapper.UserMapper;
import com.re.it211project.repository.*;
import com.re.it211project.security.CustomUserDetails;
import com.re.it211project.security.JwtProvider;
import com.re.it211project.service.AuthService;
import com.re.it211project.service.RefreshTokenService;
import com.re.it211project.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final TokenBlacklistService tokenBlacklistService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username đã tồn tại");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email đã tồn tại");
        }

        if (request.getRoleName() == RoleName.ADMIN) {
            throw new ForbiddenException("Không được đăng ký tài khoản ADMIN");
        }

        Role role = roleRepository.findByRoleName(request.getRoleName())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy role"));

        Company company = null;

        if (request.getRoleName() == RoleName.EMPLOYER) {
            company = Company.builder()
                    .companyName(request.getCompanyName())
                    .address(request.getCompanyAddress())
                    .website(request.getCompanyWebsite())
                    .description(request.getCompanyDescription())
                    .build();

            company = companyRepository.save(company);
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(UserStatus.ACTIVE)
                .role(role)
                .company(company)
                .build();

        user = userRepository.save(user);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String accessToken = jwtProvider.generateAccessToken(userDetails);
        String refreshToken = jwtProvider.generateRefreshToken(userDetails);

        refreshTokenService.createRefreshToken(user, refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy user"));

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String accessToken = jwtProvider.generateAccessToken(userDetails);
        String refreshToken = jwtProvider.generateRefreshToken(userDetails);

        refreshTokenService.createRefreshToken(user, refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        var refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        User user = refreshToken.getUser();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        String newAccessToken = jwtProvider.generateAccessToken(userDetails);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType("Bearer")
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    @Transactional
    public void logout(String token) {
        String username = jwtProvider.getUsernameFromToken(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy user"));

        tokenBlacklistService.blacklistToken(
                token,
                jwtProvider.getExpirationFromToken(token)
        );

        refreshTokenRepository.deleteByUser(user);
    }
}