package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty("id")
    private Long id;
    private String userName;
    private String email;
    private Integer age;

}
