package com.re.it211project.service.impl;

import com.re.it211project.dto.response.UserResponse;
import com.re.it211project.entity.User;
import com.re.it211project.enums.UserStatus;
import com.re.it211project.exception.NotFoundException;
import com.re.it211project.mapper.UserMapper;
import com.re.it211project.repository.UserRepository;
import com.re.it211project.security.CustomUserDetails;
import com.re.it211project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy user"));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getCurrentUser() {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userMapper.toResponse(userDetails.getUser());
    }

    @Override
    public UserResponse updateUserStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy user"));

        user.setStatus(status);
        user = userRepository.save(user);

        return userMapper.toResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy user"));

        user.setStatus(UserStatus.INACTIVE);

        userRepository.save(user);
    }
}