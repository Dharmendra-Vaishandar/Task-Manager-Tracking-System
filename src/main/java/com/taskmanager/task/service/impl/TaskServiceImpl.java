package com.taskmanager.task.service.impl;

import com.taskmanager.task.dto.*;
import com.taskmanager.task.entity.*;
import com.taskmanager.task.repository.TaskRepository;
import com.taskmanager.task.service.TaskService;
import com.taskmanager.task.specification.TaskSpecification;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.repository.UserRepository;
import com.taskmanager.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    public TaskResponse create(TaskCreateRequest request) {

        User assignee = null;
        if (request.assigneeId() != null) {
            assignee = userRepository.findById(request.assigneeId()).orElseThrow();
        }

        Task task = new Task();

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setPriority(request.priority());
        task.setStatus(TaskStatus.OPEN);
        task.setDueDate(request.dueDate());
        task.setAssignee(assignee);

        task = taskRepository.save(task);

        if (assignee != null) {
            notificationService.createNotification(assignee.getId(),
                    "Task Assigned",
                    "Task '" + task.getTitle() + "' assigned to you."
            );
        }

        return map(task);
    }

    @Override
    public TaskResponse update(Long taskId, TaskUpdateRequest request) {

        Task task = taskRepository.findById(taskId).orElseThrow();

        if (request.title() != null)
            task.setTitle(request.title());

        if (request.description() != null)
            task.setDescription(request.description());

        if (request.status() != null)
            task.setStatus(request.status());

        if (request.priority() != null)
            task.setPriority(request.priority());

        if (request.dueDate() != null)
            task.setDueDate(request.dueDate());

        taskRepository.save(task);

        return map(task);
    }

    @Override
    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskResponse get(Long taskId) {

        return map(taskRepository.findById(taskId).orElseThrow());
    }

    @Override
    public Page<TaskResponse> search(String keyword, int page, int size) {
        return taskRepository.findAll(TaskSpecification.keyword(keyword), PageRequest.of(page, size)).map(this::map);
    }

    private TaskResponse map(Task task) {

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getAssignee() != null
                        ? task.getAssignee().getId()
                        : null,
                task.getAssignee() != null
                        ? task.getAssignee().getEmail()
                        : null
        );
    }
}
