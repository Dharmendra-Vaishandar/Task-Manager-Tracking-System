package com.taskmanager.user.mapper;

import com.taskmanager.user.dto.UserResponse;
import com.taskmanager.user.entity.User;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(
            User user
    ) {

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.isEmailVerified()
        );
    }
}
