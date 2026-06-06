package com.taskmanager.notification.dto;

public record NotificationEvent(

        Long userId,

        String title,

        String message
) {
}
