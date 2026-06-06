package com.taskmanager.user.controller;

import com.taskmanager.user.dto.ChangePasswordRequest;
import com.taskmanager.user.dto.UpdateProfileRequest;
import com.taskmanager.user.dto.UserResponse;
import com.taskmanager.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse me() {
        return userService.getCurrentUser();
    }

    @PutMapping("/me")
    public UserResponse updateProfile(
            @Valid
            @RequestBody
            UpdateProfileRequest request
    ) {
        return userService.updateProfile(request);
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @Valid
            @RequestBody
            ChangePasswordRequest request
    ) {

        userService.changePassword(request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public UserResponse getById(
            @PathVariable Long id
    ) {
        return userService.getById(id);
    }

    @GetMapping
    public Page<UserResponse> search(
            @RequestParam(defaultValue = "")
            String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return userService.search(
                keyword,
                page,
                size
        );
    }
}
