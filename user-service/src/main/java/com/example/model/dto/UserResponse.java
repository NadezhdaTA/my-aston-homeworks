package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @Schema(description = "Уникальный идентификатор пользователя")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Имя пользователя")
    private String userName;

    @Schema(description = "Email пользователя")
    private String email;

    @Schema(description = "Возраст пользователя")
    private Integer age;

}
