package com.re.it211project.controller;

import com.re.it211project.security.CustomUserDetailsService;
import com.re.it211project.security.JwtProvider;
import com.re.it211project.service.TokenBlacklistService;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@AutoConfigureMockMvc(addFilters = false)
abstract class WebMvcTestSupport {

    @MockitoBean
    private JwtProvider jwtProvider;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private TokenBlacklistService tokenBlacklistService;
}
