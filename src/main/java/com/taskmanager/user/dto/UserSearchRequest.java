package com.taskmanager.user.dto;

public record UserSearchRequest(

        String keyword,

        int page,

        int size
) {}
