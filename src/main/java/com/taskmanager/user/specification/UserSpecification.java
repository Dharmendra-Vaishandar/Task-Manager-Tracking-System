package com.taskmanager.user.specification;

import com.taskmanager.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> keyword(
            String keyword
    ) {

        return (root, query, cb) ->
                cb.or(
                        cb.like(
                                cb.lower(
                                        root.get("firstName")
                                ),
                                "%" + keyword.toLowerCase() + "%"
                        ),
                        cb.like(
                                cb.lower(
                                        root.get("lastName")
                                ),
                                "%" + keyword.toLowerCase() + "%"
                        ),
                        cb.like(
                                cb.lower(
                                        root.get("email")
                                ),
                                "%" + keyword.toLowerCase() + "%"
                        )
                );
    }
}
