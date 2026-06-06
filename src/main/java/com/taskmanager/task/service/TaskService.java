package com.taskmanager.task.service;

import com.taskmanager.task.dto.*;
import org.springframework.data.domain.Page;

public interface TaskService {

    TaskResponse create(TaskCreateRequest request);

    TaskResponse update(
            Long taskId,
            TaskUpdateRequest request
    );

    void delete(Long taskId);

    TaskResponse get(Long taskId);

    Page<TaskResponse> search(
            String keyword,
            int page,
            int size
    );
}
