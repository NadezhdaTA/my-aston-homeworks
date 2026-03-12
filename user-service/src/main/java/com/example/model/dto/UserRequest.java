package com.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Имя пользователя")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String userName;

    @Schema(description = "Email пользователя")
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email пользователя должен соответствовать шаблону name@domain.com")
    private String email;

    @Schema(description = "Возраст пользователя")
    @Positive(message = "Возраст пользователя не может быть отрицательным числом")
    private Integer age;

}
