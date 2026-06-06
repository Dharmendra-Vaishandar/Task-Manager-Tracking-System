package com.taskmanager.comment.controller;

import com.taskmanager.comment.dto.CommentResponse;
import com.taskmanager.comment.dto.CreateCommentRequest;
import com.taskmanager.comment.service.CommentService;
import com.taskmanager.comment.dto.UpdateCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{taskId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(

            @PathVariable Long taskId,

            @Valid
            @RequestBody
            CreateCommentRequest request
    ) {

        return commentService
                .addComment(
                        taskId,
                        request
                );
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponse updateComment(

            @PathVariable Long commentId,

            @Valid
            @RequestBody
            UpdateCommentRequest request
    ) {

        return commentService
                .updateComment(
                        commentId,
                        request
                );
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long commentId
    ) {

        commentService.deleteComment(
                commentId
        );
    }

    @GetMapping("/{taskId}/comments")
    public List<CommentResponse> getComments(
            @PathVariable Long taskId
    ) {

        return commentService
                .getComments(
                        taskId
                );
    }
}