package com.taskmanager.notification.controller;

import com.taskmanager.notification.dto.NotificationResponse;
import com.taskmanager.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse>
    getNotifications() {

        return notificationService
                .getMyNotifications();
    }

    @PutMapping("/{notificationId}/read")
    public void markAsRead(

            @PathVariable
            Long notificationId
    ) {

        notificationService.markAsRead(
                notificationId
        );
    }
}
