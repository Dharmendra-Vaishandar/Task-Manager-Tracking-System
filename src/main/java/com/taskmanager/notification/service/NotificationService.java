package com.taskmanager.notification.service;

import com.taskmanager.notification.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

    void createNotification(
            Long userId,
            String title,
            String message
    );

    List<NotificationResponse> getMyNotifications();

    void markAsRead(
            Long notificationId
    );
}
