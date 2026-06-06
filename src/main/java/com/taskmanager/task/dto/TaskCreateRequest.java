package com.taskmanager.task.dto;

import com.taskmanager.task.entity.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskCreateRequest(

        @NotBlank
        String title,

        String description,

        @NotNull
        TaskPriority priority,

        LocalDateTime dueDate,

        Long assigneeId
) {
}
