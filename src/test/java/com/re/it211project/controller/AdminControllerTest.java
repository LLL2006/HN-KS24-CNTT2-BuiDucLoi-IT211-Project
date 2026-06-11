package com.re.it211project.controller;

import com.re.it211project.dto.response.UserResponse;
import com.re.it211project.enums.UserStatus;
import com.re.it211project.service.JobService;
import com.re.it211project.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest extends WebMvcTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JobService jobService;

    @Test
    void getAllUsers_success() throws Exception {
        UserResponse user = UserResponse.builder()
                .id(1L)
                .username("admin")
                .status(UserStatus.ACTIVE)
                .build();

        when(userService.getAllUsers(any()))
                .thenReturn(new PageImpl<>(List.of(user)));

        mockMvc.perform(get("/api/v1/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].username").value("admin"));
    }
}