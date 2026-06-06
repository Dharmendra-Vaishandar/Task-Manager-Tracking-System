package com.taskmanager.project.mapper;

import com.taskmanager.project.dto.ProjectResponse;
import com.taskmanager.project.entity.Project;

public final class ProjectMapper {

    private ProjectMapper() {
    }

    public static ProjectResponse toResponse(Project project) {

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getTeam().getId(),
                project.getOwner().getId()
        );
    }
}
