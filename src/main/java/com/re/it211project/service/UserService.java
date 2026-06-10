package com.re.it211project.service;

import com.re.it211project.dto.response.UserResponse;
import com.re.it211project.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(Long id);

    UserResponse getCurrentUser();

    UserResponse updateUserStatus(Long id, UserStatus status);

    void deleteUser(Long id);
}