package com.taskmanager.attachment.service.impl;

import com.taskmanager.attachment.dto.AttachmentResponse;
import com.taskmanager.attachment.entity.Attachment;
import com.taskmanager.attachment.exception.AttachmentNotFoundException;
import com.taskmanager.attachment.mapper.AttachmentMapper;
import com.taskmanager.attachment.repository.AttachmentRepository;
import com.taskmanager.attachment.service.AttachmentService;
import com.taskmanager.attachment.service.FileStorageService;
import com.taskmanager.task.entity.Task;
import com.taskmanager.task.exception.TaskNotFoundException;
import com.taskmanager.task.repository.TaskRepository;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachmentServiceImpl
        implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    private User getCurrentUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () ->
                                new UsernameNotFoundException(
                                        email
                                )
                );
    }

    @Override
    @Transactional
    public AttachmentResponse uploadAttachment(
            Long taskId,
            MultipartFile file
    ) {

        Task task =
                taskRepository.findById(taskId)
                        .orElseThrow(
                                () ->
                                        new TaskNotFoundException(
                                                taskId
                                        )
                        );

        String filePath =
                fileStorageService.store(
                        file
                );

        Attachment attachment =
                Attachment.builder()
                        .fileName(
                                file.getOriginalFilename()
                        )
                        .fileType(
                                file.getContentType()
                        )
                        .fileSize(
                                file.getSize()
                        )
                        .filePath(filePath)
                        .task(task)
                        .uploadedBy(
                                getCurrentUser()
                        )
                        .build();

        return AttachmentMapper.toResponse(
                attachmentRepository.save(
                        attachment
                )
        );
    }

    @Override
    public List<AttachmentResponse> getAttachments(
            Long taskId
    ) {

        return attachmentRepository
                .findByTaskId(taskId)
                .stream()
                .map(
                        AttachmentMapper::toResponse
                )
                .toList();
    }

    @Override
    public Resource downloadAttachment(
            Long attachmentId
    ) {

        Attachment attachment =
                attachmentRepository.findById(
                                attachmentId
                        )
                        .orElseThrow(
                                () ->
                                        new AttachmentNotFoundException(
                                                attachmentId
                                        )
                        );

        return fileStorageService.load(
                attachment.getFilePath()
        );
    }

    @Override
    @Transactional
    public void deleteAttachment(
            Long attachmentId
    ) {

        Attachment attachment =
                attachmentRepository.findById(
                                attachmentId
                        )
                        .orElseThrow(
                                () ->
                                        new AttachmentNotFoundException(
                                                attachmentId
                                        )
                        );

        attachmentRepository.delete(
                attachment
        );
    }
}
