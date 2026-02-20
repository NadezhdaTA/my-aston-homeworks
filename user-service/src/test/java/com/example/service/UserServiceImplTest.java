package com.example.service;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.dto.UserUpdateRequest;
import com.example.model.entity.User;
import com.example.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private final UserRepo userRepo = Mockito.mock(UserRepo.class);
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(UserServiceImplTest.class);
        userService = new UserServiceImpl(userRepo);
    }

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


    @Test
    void createUserTest_shouldCreateUser() {

        Mockito.when(userRepo.createUser(any(User.class))).thenReturn(user);

        UserResponse result = userService.createUser(userRequest);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUserName(), result.getUserName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getAge(), result.getAge());


        verify(userRepo, times(1)).createUser(any(User.class));
    }

    @Test
    void updateUser() {
        UserUpdateRequest userForUpdate = UserUpdateRequest.builder()
                .userId(5L)
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .build();

        Mockito.when(userRepo.getUserById(userForUpdate.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(userRepo.updateUser(any(User.class))).thenReturn(user);

        UserResponse updated = userService.updateUser(userForUpdate);

        assertNotNull(updated);
        assertEquals(userForUpdate.getUserId(), updated.getId());
        assertEquals(userForUpdate.getUserName(), updated.getUserName());
        assertEquals(userForUpdate.getEmail(), updated.getEmail());

    }

    @Test
    void getUserById() {
        Mockito.when(userRepo.getUserById(5L)).thenReturn(Optional.of(user));
        UserResponse result = userService.getUserById(5L);
        assertNotNull(result);
    }

    @Test
    void deleteUser() {
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userRepo.getUserById(anyLong())).thenReturn(userOptional);

        userService.deleteUser(5L);
        verify(userRepo, times(1)).deleteUser(user);
    }
}