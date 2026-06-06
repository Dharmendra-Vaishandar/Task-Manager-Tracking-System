package com.taskmanager.project.dto;

public record ProjectResponse(

        Long id,

        String name,

        String description,

        Long teamId,

        Long ownerId
) {
}
