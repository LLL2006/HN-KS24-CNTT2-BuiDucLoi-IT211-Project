package com.re.it211project.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {

    private Long id;
    private String username;
    private String email;
    private String fullName;
}