package com.taskmanager.team.service.impl;

import com.taskmanager.team.dto.*;
import com.taskmanager.team.entity.Team;
import com.taskmanager.team.entity.TeamMember;
import com.taskmanager.team.entity.TeamRole;
import com.taskmanager.team.exception.AlreadyMemberException;
import com.taskmanager.team.exception.TeamNotFoundException;
import com.taskmanager.team.mapper.TeamMapper;
import com.taskmanager.team.repository.TeamMemberRepository;
import com.taskmanager.team.repository.TeamRepository;
import com.taskmanager.team.service.TeamService;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.exception.UserNotFoundException;
import com.taskmanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final UserRepository userRepository;

    private User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () ->
                                new UsernameNotFoundException(
                                        email
                                )
                );
    }

    @Override
    @Transactional
    public TeamResponse createTeam(
            CreateTeamRequest request
    ) {

        User currentUser = getCurrentUser();

        Team team = Team.builder()
                .name(request.name())
                .description(request.description())
                .owner(currentUser)
                .build();

        Team savedTeam =
                teamRepository.save(team);

        TeamMember ownerMember =
                TeamMember.builder()
                        .team(savedTeam)
                        .user(currentUser)
                        .role(TeamRole.OWNER)
                        .build();

        teamMemberRepository.save(
                ownerMember
        );

        return TeamMapper.toResponse(
                savedTeam
        );
    }

    @Override
    public TeamResponse getTeam(
            Long teamId
    ) {

        Team team =
                teamRepository.findById(teamId)
                        .orElseThrow(
                                () ->
                                        new TeamNotFoundException(
                                                teamId
                                        )
                        );

        return TeamMapper.toResponse(team);
    }

    @Override
    @Transactional
    public TeamResponse updateTeam(
            Long teamId,
            UpdateTeamRequest request
    ) {

        Team team =
                teamRepository.findById(teamId)
                        .orElseThrow(
                                () ->
                                        new TeamNotFoundException(
                                                teamId
                                        )
                        );

        User currentUser =
                getCurrentUser();

        if (!team.getOwner()
                .getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "Only team owner can update team"
            );
        }

        team.setName(request.name());
        team.setDescription(
                request.description()
        );

        Team updated =
                teamRepository.save(team);

        return TeamMapper.toResponse(
                updated
        );
    }

    @Override
    @Transactional
    public void deleteTeam(
            Long teamId
    ) {

        Team team =
                teamRepository.findById(teamId)
                        .orElseThrow(
                                () ->
                                        new TeamNotFoundException(
                                                teamId
                                        )
                        );

        User currentUser =
                getCurrentUser();

        if (!team.getOwner()
                .getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "Only owner can delete team"
            );
        }

        teamRepository.delete(team);
    }

    @Override
    @Transactional
    public void inviteMember(
            Long teamId,
            InviteMemberRequest request
    ) {

        Team team =
                teamRepository.findById(teamId)
                        .orElseThrow(
                                () ->
                                        new TeamNotFoundException(
                                                teamId
                                        )
                        );

        if (teamMemberRepository
                .existsByTeamIdAndUserId(
                        teamId,
                        request.userId()
                )) {

            throw new AlreadyMemberException();
        }

        User user =
                userRepository.findById(
                                request.userId()
                        )
                        .orElseThrow(
                                () ->
                                        new UserNotFoundException(
                                                request.userId()
                                        )
                        );

        TeamMember member =
                TeamMember.builder()
                        .team(team)
                        .user(user)
                        .role(request.role())
                        .build();

        teamMemberRepository.save(member);
    }

    @Override
    @Transactional
    public void joinTeam(
            Long teamId
    ) {

        Team team =
                teamRepository.findById(teamId)
                        .orElseThrow(
                                () ->
                                        new TeamNotFoundException(
                                                teamId
                                        )
                        );

        User currentUser =
                getCurrentUser();

        if (teamMemberRepository
                .existsByTeamIdAndUserId(
                        teamId,
                        currentUser.getId()
                )) {

            throw new AlreadyMemberException();
        }

        TeamMember member =
                TeamMember.builder()
                        .team(team)
                        .user(currentUser)
                        .role(TeamRole.MEMBER)
                        .build();

        teamMemberRepository.save(member);
    }

    @Override
    public List<TeamMemberResponse> getMembers(
            Long teamId
    ) {

        return teamMemberRepository
                .findByTeamId(teamId)
                .stream()
                .map(
                        TeamMapper::toMemberResponse
                )
                .toList();
    }
}
