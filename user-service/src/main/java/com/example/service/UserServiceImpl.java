package com.example.service;

import com.example.mapper.UserMapper;
import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.dto.UserUpdateRequest;
import com.example.model.entity.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Создание пользователя: {}", userRequest);

        User user = userMapper.toUser(userRequest);
        user.setCreatedAt(LocalDateTime.now());
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest userRequest) {
        log.info("Обновление пользователя: {}", userRequest);

        getUserById(userRequest.getUserId());
        User user = userMapper.toUserFromUpdateRequest(userRequest);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Удаление пользователя: {}", userId);
        User user = Optional.ofNullable(userRepository.findUserById(userId)).get()
                .orElseThrow(() -> new NoSuchElementException("Пользователь с таким ID не найден."));
        userRepository.deleteUserById(userId);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        log.info("Получение пользователя по ID: {}", userId);
        User user = Optional.ofNullable(userRepository.findUserById(userId)).get()
                .orElseThrow(() -> new NoSuchElementException("Пользователь с таким ID не найден."));
        return userMapper.toUserResponse(user);
    }

}
