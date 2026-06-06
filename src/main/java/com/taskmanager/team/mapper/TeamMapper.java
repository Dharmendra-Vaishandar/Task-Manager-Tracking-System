package com.taskmanager.team.mapper;

import com.taskmanager.team.dto.TeamMemberResponse;
import com.taskmanager.team.dto.TeamResponse;
import com.taskmanager.team.entity.Team;
import com.taskmanager.team.entity.TeamMember;
import com.taskmanager.user.entity.User;

public final class TeamMapper {

    private TeamMapper() {
    }

    public static TeamResponse toResponse(
            Team team
    ) {

        User owner = team.getOwner();

        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                owner.getId(),
                owner.getEmail()
        );
    }

    public static TeamMemberResponse toMemberResponse(
            TeamMember member
    ) {

        User user = member.getUser();

        return new TeamMemberResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                member.getRole()
        );
    }
}
