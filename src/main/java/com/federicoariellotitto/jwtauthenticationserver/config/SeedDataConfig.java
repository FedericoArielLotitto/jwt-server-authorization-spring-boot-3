package com.federicoariellotitto.jwtauthenticationserver.config;

import com.federicoariellotitto.jwtauthenticationserver.domain.Role;
import com.federicoariellotitto.jwtauthenticationserver.domain.User;
import com.federicoariellotitto.jwtauthenticationserver.respositories.UserRepository;
import com.federicoariellotitto.jwtauthenticationserver.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
      if (userRepository.count() == 0) {
          User admin = User
                  .builder()
                  .firstName("Admin")
                  .lastName("Admin")
                  .email("admin@admin.com")
                  .password(passwordEncoder.encode("admin"))
                  .role(Role.ROLE_ADMIN)
                  .build();

          userService.save(admin);
          log.debug("Admin user created - {}", admin);
      }
    }
}
