package com.federicoariellotitto.jwtauthenticationserver.services;

import com.federicoariellotitto.jwtauthenticationserver.domain.Role;
import com.federicoariellotitto.jwtauthenticationserver.domain.User;
import com.federicoariellotitto.jwtauthenticationserver.dto.JwtAuthenticationResponse;
import com.federicoariellotitto.jwtauthenticationserver.dto.SignInRequest;
import com.federicoariellotitto.jwtauthenticationserver.dto.SignUpRequest;
import com.federicoariellotitto.jwtauthenticationserver.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("Invalid email or password")
        );
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();

    }
}
