package com.taskmanager.attachment.service;

import com.taskmanager.attachment.dto.AttachmentResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {

    AttachmentResponse uploadAttachment(
            Long taskId,
            MultipartFile file
    );

    List<AttachmentResponse> getAttachments(
            Long taskId
    );

    Resource downloadAttachment(
            Long attachmentId
    );

    void deleteAttachment(
            Long attachmentId
    );
}
