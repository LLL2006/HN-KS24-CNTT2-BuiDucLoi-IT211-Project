package com.re.it211project.config;

import com.re.it211project.entity.Role;
import com.re.it211project.entity.User;
import com.re.it211project.enums.RoleName;
import com.re.it211project.enums.UserStatus;
import com.re.it211project.repository.RoleRepository;
import com.re.it211project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createRole(RoleName.ADMIN);
        createRole(RoleName.EMPLOYER);
        createRole(RoleName.CANDIDATE);

        createAdminAccount();
    }

    private void createRole(RoleName roleName) {
        if (roleRepository.findByRoleName(roleName).isEmpty()) {
            Role role = Role.builder()
                    .roleName(roleName)
                    .build();

            roleRepository.save(role);
        }
    }

    private void createAdminAccount() {
        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findByRoleName(RoleName.ADMIN)
                    .orElseThrow();

            User admin = User.builder()
                    .username("admin")
                    .email("admin@gmail.com")
                    .fullName("Administrator")
                    .password(passwordEncoder.encode("123456"))
                    .status(UserStatus.ACTIVE)
                    .role(adminRole)
                    .build();

            userRepository.save(admin);
        }
    }
}