package com.re.it211project.service;

import com.re.it211project.dto.response.UserResponse;
import com.re.it211project.entity.User;
import com.re.it211project.enums.UserStatus;
import com.re.it211project.mapper.UserMapper;
import com.re.it211project.repository.UserRepository;
import com.re.it211project.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void updateUserStatus_success() {
        User user = User.builder()
                .id(1L)
                .status(UserStatus.ACTIVE)
                .build();

        UserResponse response = UserResponse.builder()
                .id(1L)
                .status(UserStatus.LOCKED)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userService.updateUserStatus(1L, UserStatus.LOCKED);

        assertEquals(UserStatus.LOCKED, result.getStatus());
        verify(userRepository).save(user);
    }
}