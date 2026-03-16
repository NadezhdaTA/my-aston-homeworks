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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public EntityModel<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Create user request: {}", userRequest);

        UserResponse userResponse = userService.createUser(userRequest);

        Link selfLink = linkTo(methodOn(UserController.class)
                .getUser(userResponse.getId()))
                .withSelfRel();

        Link updateLink = linkTo(methodOn(UserController.class)
                .updateUser(null))
                .withRel("update");
        return EntityModel.of(userResponse, updateLink, selfLink);
    }

    @PutMapping
    @Operation(summary = "Обновление пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлен")
    public EntityModel<UserResponse> updateUser(@Valid @RequestBody UserUpdateRequest userRequest) {
        log.info("Update user request: {}", userRequest);
        UserResponse userResponse = userService.updateUser(userRequest);

        Link selfLink = linkTo(methodOn(UserController.class)
                .updateUser(userRequest))
                .withSelfRel();

        Link getLink = linkTo(methodOn(UserController.class)
                .getUser(userRequest.getUserId()))
                .withRel("getUser");

        return EntityModel.of(userResponse, selfLink, getLink);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение пользователя по ID",
            description = "Возвращает информацию о пользователе по его уникальному идентификатору"
    )
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    public EntityModel<UserResponse> getUser(@PathVariable Long id) {
        log.info("Get user request: {}", id);
        UserResponse userResponse = userService.getUserById(id);

        Link selfLink = linkTo(methodOn(UserController.class)
                .getUser(id))
                .withSelfRel();

        Link updateLink = linkTo(methodOn(UserController.class)
                .updateUser(null))
                .withRel("update");

        return EntityModel.of(userResponse, selfLink, updateLink);
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
