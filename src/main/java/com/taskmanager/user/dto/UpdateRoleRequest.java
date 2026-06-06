package com.taskmanager.user.dto;

import com.taskmanager.user.entity.Role;

public record UpdateRoleRequest(
        Role role
) {}
