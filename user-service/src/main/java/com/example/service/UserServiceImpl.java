package com.example.service;

import com.example.model.User;
import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.dto.UserUpdateRequest;
import com.example.model.mapper.UserMapper;
import com.example.repository.UserRepo;
import com.example.repository.UserRepoImpl;
import com.example.util.UserValidator;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo = new UserRepoImpl();

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        log.info("Сохранение пользователя: {}", userRequest);
        try {
            UserValidator.validateUserRequest(userRequest);
        } catch (IllegalArgumentException e) {
            log.error("Данные пользователя не соответствуют заданным параметрам: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Что-то пошло не так: {}", e.getMessage());
        }

        User user = UserMapper.INSTANCE.toUser(userRequest);
        user.setCreatedAt(LocalDateTime.now());
        user = userRepo.createUser(user);
        return UserMapper.INSTANCE.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest userRequest) {
        log.info("Обновление пользователя: {}", userRequest);
        try {
            UserValidator.validateUserUpdateRequest(userRequest);
        } catch (IllegalArgumentException e) {
            log.error("Данные пользователя не соответствуют заданным параметрам: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Что-то пошло не так: {}", e.getMessage());
        }

        getUserById(userRequest.getUserId());
        User user = UserMapper.INSTANCE.toUserFromUpdateRequest(userRequest);
        user = userRepo.updateUser(user);
        return UserMapper.INSTANCE.toUserResponse(user);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Удаление пользователя: {}", userId);
        User user = Optional.ofNullable(userRepo.getUserById(userId)).get()
                .orElseThrow(() -> new NoSuchElementException("Пользователь с таким ID не найден."));
        userRepo.deleteUser(user);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        log.info("Получение пользователя по ID: {}", userId);
        User user = Optional.ofNullable(userRepo.getUserById(userId)).get()
                .orElseThrow(() -> new NoSuchElementException("Пользователь с таким ID не найден."));
        return UserMapper.INSTANCE.toUserResponse(user);
    }

}
