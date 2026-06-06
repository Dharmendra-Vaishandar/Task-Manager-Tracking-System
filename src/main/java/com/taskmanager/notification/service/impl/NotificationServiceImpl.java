package com.taskmanager.notification.service.impl;

import com.taskmanager.notification.dto.NotificationEvent;
import com.taskmanager.notification.dto.NotificationResponse;
import com.taskmanager.notification.entity.Notification;
import com.taskmanager.notification.repository.NotificationRepository;
import com.taskmanager.notification.service.NotificationService;
import com.taskmanager.notification.websocket.NotificationPublisher;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    private final NotificationPublisher publisher;

    private User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void createNotification(

            Long userId,

            String title,

            String message
    ) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow();

        Notification notification =
                Notification.builder()
                        .title(title)
                        .message(message)
                        .recipient(user)
                        .readFlag(false)
                        .build();

        Notification saved =
                notificationRepository.save(
                        notification
                );

        publisher.publish(
                new NotificationEvent(
                        userId,
                        saved.getTitle(),
                        saved.getMessage()
                )
        );
    }

    @Override
    public List<NotificationResponse>
    getMyNotifications() {

        return notificationRepository
                .findByRecipientIdOrderByCreatedAtDesc(
                        getCurrentUser().getId()
                )
                .stream()
                .map(
                        n -> new NotificationResponse(
                                n.getId(),
                                n.getTitle(),
                                n.getMessage(),
                                n.isReadFlag(),
                                n.getCreatedAt()
                        )
                )
                .toList();
    }

    @Override
    @Transactional
    public void markAsRead(
            Long notificationId
    ) {

        Notification notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow();

        notification.setReadFlag(true);

        notificationRepository.save(
                notification
        );
    }
}
