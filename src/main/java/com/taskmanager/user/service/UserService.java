package com.taskmanager.user.service;

import com.taskmanager.user.dto.*;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponse getCurrentUser();

    UserResponse getById(Long id);

    UserResponse updateProfile(
            UpdateProfileRequest request
    );

    void changePassword(
            ChangePasswordRequest request
    );

    Page<UserResponse> search(
            String keyword,
            int page,
            int size
    );

    UserResponse updateRole(
            Long id,
            UpdateRoleRequest request
    );

    UserResponse deactivate(
            Long id
    );
}
