package com.taskmanager.attachment.dto;

import java.time.LocalDateTime;

public record AttachmentResponse(

        Long id,

        String fileName,

        String fileType,

        Long fileSize,

        Long taskId,

        Long uploadedBy,

        LocalDateTime uploadedAt
) {
}
