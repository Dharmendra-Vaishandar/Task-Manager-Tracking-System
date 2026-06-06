package com.taskmanager.team.exception;

public class TeamNotFoundException
        extends RuntimeException {

    public TeamNotFoundException(Long teamId) {

        super("Team not found : " + teamId);
    }
}
