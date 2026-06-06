package com.taskmanager.user.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String str) {
        super("User not found : " + str);
    }
}

