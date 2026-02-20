package com.example.mapper;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.dto.UserUpdateRequest;
import com.example.model.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toUser() {
        UserRequest userRequest = UserRequest.builder()
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .build();

        User user = userMapper.toUser(userRequest);

        assertEquals("user", user.getUserName());
        assertEquals("email@domain.com", user.getEmail());
        assertEquals(20, user.getAge());

    }

    @Test
    void toUserResponse() {
        User user = User.builder()
                .id(5L)
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0))
                .build();

        UserResponse userResponse = userMapper.toUserResponse(user);

        assertEquals(5, userResponse.getId());
        assertEquals("user", userResponse.getUserName());
        assertEquals("email@domain.com", userResponse.getEmail());
        assertEquals(20, userResponse.getAge());

    }

    @Test
    void toUserFromUpdateRequest() {
        UserUpdateRequest userForUpdate = UserUpdateRequest.builder()
                .userId(5L)
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .build();

        User user = userMapper.toUserFromUpdateRequest(userForUpdate);

        assertEquals(5, user.getId());
        assertEquals("user", user.getUserName());
        assertEquals("email@domain.com", user.getEmail());
        assertEquals(20, user.getAge());

    }
}