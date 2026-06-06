package com.taskmanager.project.service;

import com.taskmanager.project.dto.*;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(CreateProjectRequest request);

    ProjectResponse updateProject(Long projectId, UpdateProjectRequest request);

    ProjectResponse getProject(Long projectId);

    List<ProjectResponse> getProjectsByTeam(Long teamId);

    void deleteProject(Long projectId);

    void addMember(Long projectId, AssignProjectMemberRequest request);
}
