package com.taskmanager.project.service.impl;

import com.taskmanager.project.dto.AssignProjectMemberRequest;
import com.taskmanager.project.dto.CreateProjectRequest;
import com.taskmanager.project.dto.ProjectResponse;
import com.taskmanager.project.dto.UpdateProjectRequest;
import com.taskmanager.project.entity.Project;
import com.taskmanager.project.entity.ProjectMember;
import com.taskmanager.project.entity.ProjectRole;
import com.taskmanager.project.exception.ProjectMemberAlreadyExistsException;
import com.taskmanager.project.exception.ProjectNotFoundException;
import com.taskmanager.project.mapper.ProjectMapper;
import com.taskmanager.project.repository.ProjectMemberRepository;
import com.taskmanager.project.repository.ProjectRepository;
import com.taskmanager.project.service.ProjectService;
import com.taskmanager.team.entity.Team;
import com.taskmanager.team.exception.TeamNotFoundException;
import com.taskmanager.team.repository.TeamRepository;
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
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    @Transactional
    public ProjectResponse createProject(CreateProjectRequest request) {

        User currentUser = getCurrentUser();

        Team team = teamRepository.findById(request.teamId()).orElseThrow(() -> new TeamNotFoundException(request.teamId()));

        Project project = Project.builder()
                .name(request.name())
                .description(
                        request.description()
                )
                .team(team)
                .owner(currentUser)
                .build();

        Project savedProject = projectRepository.save(project);

        ProjectMember owner = ProjectMember.builder()
                .project(savedProject)
                .user(currentUser)
                .role(ProjectRole.OWNER)
                .build();

        projectMemberRepository.save(owner);

        return ProjectMapper.toResponse(savedProject);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(
            Long projectId,
            UpdateProjectRequest request
    ) {

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));

        User currentUser = getCurrentUser();

        if (!project.getOwner()
                .getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException("Only project owner can update project");
        }

        project.setName(request.name());
        project.setDescription(request.description());

        return ProjectMapper.toResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponse getProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        return ProjectMapper.toResponse(project);
    }

    @Override
    public List<ProjectResponse> getProjectsByTeam(Long teamId) {

        return projectRepository.findByTeamId(teamId).stream().map(ProjectMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public void deleteProject(
            Long projectId
    ) {

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));

        User currentUser = getCurrentUser();

        if (!project.getOwner()
                .getId()
                .equals(currentUser.getId())) {
            throw new RuntimeException("Only owner can delete project");
        }

        projectRepository.delete(project);
    }

    @Override
    @Transactional
    public void addMember(Long projectId, AssignProjectMemberRequest request) {

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));

        if (projectMemberRepository.existsByProjectIdAndUserId(projectId, request.userId())) {
            throw new ProjectMemberAlreadyExistsException();
        }

        User user = userRepository.findById(request.userId()).orElseThrow(() -> new UserNotFoundException(request.userId()));

        ProjectMember member = ProjectMember.builder()
                .project(project)
                .user(user)
                .role(request.role())
                .build();

        projectMemberRepository.save(member);
    }
}