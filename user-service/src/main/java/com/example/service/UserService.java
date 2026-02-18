package com.example.service;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.dto.UserUpdateRequest;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(UserUpdateRequest userRequest);
    void deleteUser(Long userId);
    UserResponse getUserById(Long userId);
}
