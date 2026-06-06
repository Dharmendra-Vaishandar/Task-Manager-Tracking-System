package com.taskmanager.task.dto;

import com.taskmanager.task.entity.TaskPriority;
import com.taskmanager.task.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(

        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime dueDate,
        Long assigneeId,
        String assigneeEmail
) {
}
