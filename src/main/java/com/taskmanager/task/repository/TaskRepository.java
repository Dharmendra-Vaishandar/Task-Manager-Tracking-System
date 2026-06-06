package com.taskmanager.task.repository;

import com.taskmanager.task.entity.Task;
import com.taskmanager.task.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends
        JpaRepository<Task, Long>,
        JpaSpecificationExecutor<Task> {

    Page<Task> findByAssigneeId(
            Long assigneeId,
            Pageable pageable
    );

    Page<Task> findByStatus(
            TaskStatus status,
            Pageable pageable
    );
}
