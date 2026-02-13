package com.example.model.dto;

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

    @NotNull
    @Positive
    private Long userId;
    private String userName;

    @Email(message = "Email пользователя должен соответствовать шаблону user@mail.ru")
    private String email;

    @Positive(message = "Возраст пользователя не может быть отрицательным числом")
    private Integer age;
}
