package com.taskmanager.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

public class AuthRequest {

    @Data
    public static class Register {
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be 3-50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username may only contain letters, numbers, and underscores")
        private String username;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be at least 8 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
        private String password;

        @Size(max = 50)
        private String firstName;

        @Size(max = 50)
        private String lastName;
    }

    @Data
    public static class Login {
        @NotBlank(message = "Email or username is required")
        private String usernameOrEmail;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    public static class RefreshToken {
        @NotBlank
        private String refreshToken;
    }

    @Data
    public static class UpdateProfile {
        @Size(max = 50)
        private String firstName;

        @Size(max = 50)
        private String lastName;

        @Size(max = 500)
        private String bio;
    }

    @Data
    public static class ChangePassword {
        @NotBlank
        private String currentPassword;

        @NotBlank
        @Size(min = 8, max = 100)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
        private String newPassword;
    }
}
