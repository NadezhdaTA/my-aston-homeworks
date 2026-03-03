package com.example.controller;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.dto.UserUpdateRequest;
import com.example.model.entity.User;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    UserRequest userRequest = UserRequest.builder()
            .userName("user")
            .email("email@domain.com")
            .age(20)
            .build();

    User user = User.builder()
            .id(5L)
            .userName("user")
            .email("email@domain.com")
            .age(20)
            .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0))
            .build();

    UserResponse userResponse = UserResponse.builder()
            .id(5L)
            .userName("user")
            .email("email@domain.com")
            .age(20)
            .build();

    UserUpdateRequest userForUpdate = UserUpdateRequest.builder()
            .userId(5L)
            .userName("user")
            .email("email@domain.com")
            .age(20)
            .build();

    @Test
    public void createUserTest() throws Exception {
        when(userService.createUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/users")
                .content(objectMapper.writeValueAsString(userRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(userResponse.getId()), Long.class))
                .andExpect(jsonPath("$.userName", is(userResponse.getUserName()), String.class))
                .andExpect(jsonPath("$.email", is(userResponse.getEmail()), String.class))
                .andExpect(jsonPath("$.age", is(userResponse.getAge()), Integer.class));

    }

    @Test
    public void updateUserTest() throws Exception {
        when(userService.updateUser(any(UserUpdateRequest.class))).thenReturn(userResponse);

        mockMvc.perform(put("/api/users")
        .content(objectMapper.writeValueAsString(userForUpdate))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userResponse.getId()), Long.class))
                .andExpect(jsonPath("$.userName", is(userResponse.getUserName()), String.class))
                .andExpect(jsonPath("$.email", is(userResponse.getEmail()), String.class))
                .andExpect(jsonPath("$.age", is(userResponse.getAge()), Integer.class));
    }

    @Test
    public void getUser() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(userResponse);

        mockMvc.perform(get("/api/users/{id}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userResponse.getId()), Long.class))
                .andExpect(jsonPath("$.userName", is(userResponse.getUserName()), String.class))
                .andExpect(jsonPath("$.email", is(userResponse.getEmail()), String.class))
                .andExpect(jsonPath("$.age", is(userResponse.getAge()), Integer.class));

    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 5L))
                .andExpect(status().isNoContent());
    }
}
