package com.re.it211project.repository;

import com.re.it211project.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository
        extends JpaRepository<TokenBlacklist, Long> {

    boolean existsByToken(String token);

}