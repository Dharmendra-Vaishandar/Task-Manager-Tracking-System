package com.taskmanager.comment.service;

import com.taskmanager.comment.dto.CommentResponse;
import com.taskmanager.comment.dto.CreateCommentRequest;
import com.taskmanager.comment.dto.UpdateCommentRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {

    CommentResponse addComment(
            Long taskId,
            CreateCommentRequest request
    );

    @Transactional
    CommentResponse updateComment(
            Long commentId,
            UpdateCommentRequest request
    );

    @Transactional
    void deleteComment(
            Long commentId
    );

    List<CommentResponse> getComments(
            Long taskId
    );
}
