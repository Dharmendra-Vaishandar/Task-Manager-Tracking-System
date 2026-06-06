package com.taskmanager.user.dto;

import com.taskmanager.user.entity.Role;
import com.taskmanager.user.entity.UserStatus;

public record UserResponse(

        Long id,
        String firstName,
        String lastName,
        String email,
        Role role,
        UserStatus status,
        boolean emailVerified
) {}
