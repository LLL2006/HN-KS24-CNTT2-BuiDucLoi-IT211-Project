package com.re.it211project.service;

import com.re.it211project.entity.RefreshToken;
import com.re.it211project.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user, String token);

    RefreshToken verifyRefreshToken(String token);
}