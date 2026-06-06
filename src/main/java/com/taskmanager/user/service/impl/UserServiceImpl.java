package com.taskmanager.user.service.impl;

import com.taskmanager.user.dto.ChangePasswordRequest;
import com.taskmanager.user.dto.UpdateProfileRequest;
import com.taskmanager.user.dto.UpdateRoleRequest;
import com.taskmanager.user.dto.UserResponse;
import com.taskmanager.user.entity.User;
import com.taskmanager.user.entity.UserStatus;
import com.taskmanager.user.exception.InvalidPasswordException;
import com.taskmanager.user.exception.UserNotFoundException;
import com.taskmanager.user.exception.UsernameNotFoundException;
import com.taskmanager.user.mapper.UserMapper;
import com.taskmanager.user.repository.UserRepository;
import com.taskmanager.user.service.UserService;
import com.taskmanager.user.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Returns currently authenticated user.
     */
    private User getCurrentAuthenticatedUser() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Authenticated user not found: " + email
                        ));
    }

    @Override
    public UserResponse getCurrentUser() {

        return UserMapper.toResponse(
                getCurrentAuthenticatedUser()
        );
    }

    @Override
    public UserResponse getById(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id));

        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateProfile(
            UpdateProfileRequest request
    ) {

        User user = getCurrentAuthenticatedUser();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

        User savedUser =
                userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public void changePassword(
            ChangePasswordRequest request
    ) {

        User user = getCurrentAuthenticatedUser();

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.oldPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {
            throw new InvalidPasswordException();
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.newPassword()
                )
        );

        userRepository.save(user);
    }

    @Override
    public Page<UserResponse> search(
            String keyword,
            int page,
            int size
    ) {

        return userRepository
                .findAll(
                        UserSpecification.keyword(
                                keyword == null ? "" : keyword
                        ),
                        PageRequest.of(
                                page,
                                size,
                                Sort.by(Sort.Direction.ASC, "firstName")
                        )
                )
                .map(UserMapper::toResponse);
    }

    @Override
    @Transactional
    public UserResponse updateRole(
            Long userId,
            UpdateRoleRequest request
    ) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(userId));

        user.setRole(request.role());

        User savedUser =
                userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse deactivate(
            Long userId
    ) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(userId));

        user.setStatus(UserStatus.INACTIVE);

        User savedUser =
                userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }
}