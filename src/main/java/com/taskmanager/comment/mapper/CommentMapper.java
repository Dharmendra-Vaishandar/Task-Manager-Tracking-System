package com.taskmanager.comment.mapper;

import com.taskmanager.comment.dto.CommentResponse;
import com.taskmanager.comment.entity.Comment;

public final class CommentMapper {

    private CommentMapper() {
    }

    public static CommentResponse toResponse(
            Comment comment
    ) {

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getId(),
                comment.getAuthor().getEmail(),
                comment.getTask().getId(),
                comment.getCreatedAt()
        );
    }
}
