package com.taskmanager.task.controller;

import com.taskmanager.task.dto.*;
import com.taskmanager.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(
            @Valid
            @RequestBody
            TaskCreateRequest request
    ) {

        return taskService.create(request);
    }

    @GetMapping("/{id}")
    public TaskResponse get(
            @PathVariable Long id
    ) {

        return taskService.get(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest request
    ) {

        return taskService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {

        taskService.delete(id);
    }

    @GetMapping("/search")
    public Page<TaskResponse> search(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return taskService.search(
                keyword,
                page,
                size
        );
    }
}
