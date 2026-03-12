package com.example.controller;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.dto.UserUpdateRequest;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API для управления пользователями")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового пользователя")
    @ApiResponse(responseCode = "201", description = "Пользователь создан")
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Create user request: {}", userRequest);
        return userService.createUser(userRequest);
    }

    @PutMapping
    @Operation(summary = "Обновление пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлен")
    public UserResponse updateUser(@Valid @RequestBody UserUpdateRequest userRequest) {
        log.info("Update user request: {}", userRequest);
        return userService.updateUser(userRequest);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение пользователя по ID",
            description = "Возвращает информацию о пользователе по его уникальному идентификатору"
    )
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public UserResponse getUser(@PathVariable Long id) {
        log.info("Get user request: {}", id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление пользователя")
    @ApiResponse(responseCode = "204", description = "Пользователь удален")
    public void deleteUser(@PathVariable Long id) {
        log.info("Delete user request: {}", id);
        userService.deleteUser(id);
    }
}
