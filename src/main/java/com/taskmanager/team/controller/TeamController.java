package com.taskmanager.team.controller;

import com.taskmanager.team.dto.*;
import com.taskmanager.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponse createTeam(
            @Valid
            @RequestBody
            CreateTeamRequest request
    ) {

        return teamService.createTeam(
                request
        );
    }

    @GetMapping("/{teamId}")
    public TeamResponse getTeam(
            @PathVariable Long teamId
    ) {

        return teamService.getTeam(
                teamId
        );
    }

    @PutMapping("/{teamId}")
    public TeamResponse updateTeam(

            @PathVariable Long teamId,

            @Valid
            @RequestBody
            UpdateTeamRequest request
    ) {

        return teamService.updateTeam(
                teamId,
                request
        );
    }

    @DeleteMapping("/{teamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(
            @PathVariable Long teamId
    ) {

        teamService.deleteTeam(
                teamId
        );
    }

    @PostMapping("/{teamId}/invite")
    @ResponseStatus(HttpStatus.OK)
    public void inviteMember(

            @PathVariable Long teamId,

            @Valid
            @RequestBody
            InviteMemberRequest request
    ) {

        teamService.inviteMember(
                teamId,
                request
        );
    }

    @PostMapping("/{teamId}/join")
    @ResponseStatus(HttpStatus.OK)
    public void joinTeam(
            @PathVariable Long teamId
    ) {
        teamService.joinTeam(teamId);
    }

    @GetMapping("/{teamId}/members")
    public List<TeamMemberResponse> members(
            @PathVariable Long teamId
    ) {

        return teamService.getMembers(
                teamId
        );
    }
}
