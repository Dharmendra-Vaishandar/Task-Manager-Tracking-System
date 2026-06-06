package com.taskmanager.team.exception;

public class AlreadyMemberException
        extends RuntimeException {

    public AlreadyMemberException() {

        super("User is already a team member");
    }
}
