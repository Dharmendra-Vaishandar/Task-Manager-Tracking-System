package com.taskmanager.task.dto;

import com.taskmanager.task.entity.TaskPriority;
import com.taskmanager.task.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskUpdateRequest(

        String title,

        String description,

        TaskPriority priority,

        TaskStatus status,

        LocalDateTime dueDate,

        Long assigneeId
) {
}
