package com.taskmanager.team.dto;

import com.taskmanager.team.entity.TeamRole;
import jakarta.validation.constraints.NotNull;

public record InviteMemberRequest(

        @NotNull
        Long userId,

        @NotNull
        TeamRole role
) {
}
