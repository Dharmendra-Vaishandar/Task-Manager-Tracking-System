package com.taskmanager.notification.dto;

import java.time.LocalDateTime;

public record NotificationResponse(

        Long id,

        String title,

        String message,

        boolean readFlag,

        LocalDateTime createdAt
) {
}
