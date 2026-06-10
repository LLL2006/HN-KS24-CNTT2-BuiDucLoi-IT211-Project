package com.re.it211project.dto.request;

import com.re.it211project.enums.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class                                                        RegisterRequest {

    @NotBlank(message = "Username không được để trống")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Full name không được để trống")
    private String fullName;

    @NotBlank(message = "Password không được để trống")
    private String password;

    private String phone;

    @NotNull(message = "Role không được để trống")
    private RoleName roleName;

    private String companyName;

    private String companyAddress;

    private String companyWebsite;

    private String companyDescription;
}