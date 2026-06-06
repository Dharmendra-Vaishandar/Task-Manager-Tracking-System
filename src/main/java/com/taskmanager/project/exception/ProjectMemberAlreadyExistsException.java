package com.taskmanager.project.exception;

public class ProjectMemberAlreadyExistsException extends RuntimeException {

    public ProjectMemberAlreadyExistsException() {
        super("User already assigned to project");
    }
}
