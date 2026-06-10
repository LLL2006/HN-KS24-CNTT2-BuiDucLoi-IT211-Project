package com.re.it211project.mapper;

import com.re.it211project.dto.response.UserResponse;
import com.re.it211project.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final CompanyMapper companyMapper;

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .cvUrl(user.getCvUrl())
                .roleName(user.getRole().getRoleName())
                .status(user.getStatus())
                .build();
    }
}