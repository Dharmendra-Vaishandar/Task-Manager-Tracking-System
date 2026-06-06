package com.taskmanager.user.exception;

public class InvalidPasswordException
        extends RuntimeException {

    public InvalidPasswordException() {

        super("Old password incorrect");
    }
}