package com.taskmanager.team.dto;

import com.taskmanager.team.entity.TeamRole;

public record TeamMemberResponse(

        Long userId,

        String email,

        String firstName,

        String lastName,

        TeamRole role
) {
}
