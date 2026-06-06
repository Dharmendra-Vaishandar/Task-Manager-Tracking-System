package com.taskmanager.team.dto;

public record TeamResponse(

        Long id,

        String name,

        String description,

        Long ownerId,

        String ownerEmail
) {
}
