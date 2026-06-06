package com.taskmanager.project.dto;

import com.taskmanager.project.entity.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record AssignProjectMemberRequest(

        @NotNull
        Long userId,

        @NotNull
        ProjectRole role
) {
}
