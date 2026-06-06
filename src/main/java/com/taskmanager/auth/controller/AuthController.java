package com.taskmanager.auth.controller;

import com.taskmanager.auth.dto.LoginRequest;
import com.taskmanager.auth.dto.LoginResponse;
import com.taskmanager.auth.dto.RegisterRequest;
import com.taskmanager.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody
            RegisterRequest request
    ) {

        authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody
            LoginRequest request
    ) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}
