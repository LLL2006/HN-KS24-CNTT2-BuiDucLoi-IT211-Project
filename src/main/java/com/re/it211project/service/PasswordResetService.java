package com.re.it211project.service;

import com.re.it211project.dto.request.ChangePasswordRequest;
import com.re.it211project.dto.request.ForgotPasswordRequest;
import com.re.it211project.dto.request.ResetPasswordRequest;

public interface PasswordResetService {

    void changePassword(ChangePasswordRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}