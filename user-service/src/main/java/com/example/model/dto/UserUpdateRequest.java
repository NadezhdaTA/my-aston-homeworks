package com.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Schema(description = "Уникальный идентификатор пользователя")
    @NotNull
    @Positive
    private Long userId;

    @Schema(description = "Имя пользователя")
    private String userName;

    @Schema(description = "Email пользователя")
    @Email(message = "Email пользователя должен соответствовать шаблону user@mail.ru")
    private String email;

    @Schema(description = "Возраст пользователя")
    @Positive(message = "Возраст пользователя не может быть отрицательным числом")
    private Integer age;
}
