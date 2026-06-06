package com.taskmanager.attachment.mapper;

import com.taskmanager.attachment.dto.AttachmentResponse;
import com.taskmanager.attachment.entity.Attachment;

public final class AttachmentMapper {

    private AttachmentMapper() {
    }

    public static AttachmentResponse toResponse(
            Attachment attachment
    ) {

        return new AttachmentResponse(
                attachment.getId(),
                attachment.getFileName(),
                attachment.getFileType(),
                attachment.getFileSize(),
                attachment.getTask().getId(),
                attachment.getUploadedBy().getId(),
                attachment.getCreatedAt()
        );
    }
}
