package com.taskmanager.notification.websocket;

import com.taskmanager.notification.dto.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publish(
            NotificationEvent event
    ) {

        messagingTemplate.convertAndSend(

                "/topic/notifications/"
                        + event.userId(),

                event
        );
    }
}
