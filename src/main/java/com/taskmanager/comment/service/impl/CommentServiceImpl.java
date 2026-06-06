package com.taskmanager.comment.service.impl;

import com.taskmanager.comment.dto.CommentResponse;
import com.taskmanager.comment.dto.CreateCommentRequest;
import com.taskmanager.comment.dto.UpdateCommentRequest;
import com.taskmanager.comment.entity.Comment;
import com.taskmanager.comment.exception.CommentNotFoundException;
import com.taskmanager.comment.mapper.CommentMapper;
import com.taskmanager.comment.repository.CommentRepository;
import com.taskmanager.comment.service.CommentService;
import com.taskmanager.notification.service.NotificationService;
import com.taskmanager.task.entity.Task;
import com.taskmanager.task.exception.TaskNotFoundException;
import com.taskmanager.task.repository.TaskRepository;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final NotificationService notificationService;

    private User getCurrentUser() {

        String email = SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    @Transactional
    public CommentResponse addComment(Long taskId, CreateCommentRequest request) {

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        Comment comment = Comment.builder().content(request.content()).task(task).author(getCurrentUser()).build();

        CommentResponse response = CommentMapper.toResponse(commentRepository.save(comment));

        if (task.getAssignee() != null) {
            notificationService.createNotification(
                    task.getAssignee().getId(),
                    "New Comment",
                    "A new comment was added to task "
                            + task.getTitle()
            );
        }
        return response;
    }

    @Transactional
    @Override
    public CommentResponse updateComment(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        comment.setContent(request.content());
        return CommentMapper.toResponse(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentResponse> getComments(Long taskId) {

        return commentRepository
                .findByTaskIdOrderByCreatedAtAsc(taskId)
                .stream().map(CommentMapper::toResponse).toList();
    }
}