package com.example.common.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    @NonNull
    private Long id;
    @NonNull
    private String email;
    @NonNull
    private UserStatus status;
}
