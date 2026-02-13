package com.example.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String userName;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Email(message = "Email пользователя должен соответствовать шаблону name@domain.com")
    private String email;

    @Positive(message = "Возраст пользователя не может быть отрицательным числом")
    private Integer age;

}
