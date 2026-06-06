package com.taskmanager.project.controller;

import com.taskmanager.project.dto.AssignProjectMemberRequest;
import com.taskmanager.project.dto.CreateProjectRequest;
import com.taskmanager.project.dto.ProjectResponse;
import com.taskmanager.project.dto.UpdateProjectRequest;
import com.taskmanager.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(

            @Valid
            @RequestBody
            CreateProjectRequest request
    ) {

        return projectService.createProject(
                request
        );
    }

    @GetMapping("/{projectId}")
    public ProjectResponse getProject(
            @PathVariable Long projectId
    ) {

        return projectService.getProject(
                projectId
        );
    }

    @PutMapping("/{projectId}")
    public ProjectResponse updateProject(

            @PathVariable Long projectId,

            @Valid
            @RequestBody
            UpdateProjectRequest request
    ) {

        return projectService.updateProject(
                projectId,
                request
        );
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(
            @PathVariable Long projectId
    ) {

        projectService.deleteProject(
                projectId
        );
    }

    @GetMapping("/team/{teamId}")
    public List<ProjectResponse>
    getProjectsByTeam(
            @PathVariable Long teamId
    ) {

        return projectService.getProjectsByTeam(
                teamId
        );
    }

    @PostMapping("/{projectId}/members")
    @ResponseStatus(HttpStatus.OK)
    public void addMember(

            @PathVariable Long projectId,

            @Valid
            @RequestBody
            AssignProjectMemberRequest request
    ) {

        projectService.addMember(
                projectId,
                request
        );
    }
}
