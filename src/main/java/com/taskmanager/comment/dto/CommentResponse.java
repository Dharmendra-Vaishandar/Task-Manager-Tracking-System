package com.taskmanager.comment.dto;

import java.time.LocalDateTime;

public record CommentResponse(

        Long id,

        String content,

        Long authorId,

        String authorEmail,

        Long taskId,

        LocalDateTime createdAt
) {
}
