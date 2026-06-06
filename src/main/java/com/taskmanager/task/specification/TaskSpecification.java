package com.taskmanager.task.specification;

import com.taskmanager.task.entity.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> keyword(
            String keyword
    ) {

        return (root, query, cb) ->
                cb.or(
                        cb.like(
                                cb.lower(root.get("title")),
                                "%" + keyword.toLowerCase() + "%"
                        ),
                        cb.like(
                                cb.lower(root.get("description")),
                                "%" + keyword.toLowerCase() + "%"
                        )
                );
    }
}
