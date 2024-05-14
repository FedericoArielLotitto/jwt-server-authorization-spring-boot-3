package com.federicoariellotitto.jwtauthenticationserver.services;

import com.federicoariellotitto.jwtauthenticationserver.domain.User;
import com.federicoariellotitto.jwtauthenticationserver.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setCreatedAt(LocalDateTime.now());
        } else {
            user.setUpdatedAt(LocalDateTime.now());
        }
        return userRepository.save(user);
    }
}
