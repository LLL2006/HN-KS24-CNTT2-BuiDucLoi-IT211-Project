package com.re.it211project.service;

import java.util.Date;

public interface TokenBlacklistService {

    void blacklistToken(String token, Date expiration);

    boolean isBlacklisted(String token);
}