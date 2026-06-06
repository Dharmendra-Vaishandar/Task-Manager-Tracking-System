package com.taskmanager.auth.dto;

import com.taskmanager.user.entity.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @Email
        String email,

        @Size(min = 8)
        String password,

        UserStatus status

) {
    public static record LoginResponse(
            String token
    ) {}
}