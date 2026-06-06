package com.taskmanager.team.service;

import com.taskmanager.team.dto.*;

import java.util.List;

public interface TeamService {

    TeamResponse createTeam(
            CreateTeamRequest request
    );

    TeamResponse getTeam(
            Long teamId
    );

    TeamResponse updateTeam(
            Long teamId,
            UpdateTeamRequest request
    );

    void deleteTeam(
            Long teamId
    );

    void inviteMember(
            Long teamId,
            InviteMemberRequest request
    );

    void joinTeam(
            Long teamId
    );

    List<TeamMemberResponse> getMembers(
            Long teamId
    );
}
