package com.re.it211project.dto.response;

import com.re.it211project.enums.RoleName;
import com.re.it211project.enums.UserStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private String fullName;

    private String phone;

    private String cvUrl;

    private RoleName roleName;

    private UserStatus status;

}