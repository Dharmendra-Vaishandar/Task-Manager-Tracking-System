package com.taskmanager.auth.service;

import com.taskmanager.auth.dto.LoginRequest;
import com.taskmanager.auth.dto.LoginResponse;
import com.taskmanager.auth.dto.RegisterRequest;
import com.taskmanager.security.JwtService;
import com.taskmanager.user.entity.Role;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.entity.UserStatus;
import com.taskmanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(
                request.email())) {

            throw new RuntimeException(
                    "Email already exists");
        }

        // Default status to ACTIVE if not provided
        UserStatus userStatus = request.status() != null ? request.status() : UserStatus.ACTIVE;

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(
                        encoder.encode(
                                request.password()))
                .role(Role.ROLE_USER)
                .status(userStatus)
                .emailVerified(false)
                .build();

        userRepository.save(user);
    }

    public LoginResponse login(
            LoginRequest request
    ) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                ));

        String token =
                jwtService.generateToken(
                        request.email());

        return new LoginResponse(token);
    }
}