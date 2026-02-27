package com.example.service;

import com.example.mapper.UserMapper;
import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.dto.UserUpdateRequest;
import com.example.model.entity.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userService;

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


    @Test
    void createUserTest_shouldCreateUser() {

        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(userMapper.toUser(userRequest)).thenReturn(user);
        Mockito.when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.createUser(userRequest);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUserName(), result.getUserName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getAge(), result.getAge());


        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toUser(userRequest);
        verify(userMapper, times(1)).toUserResponse(user);
    }

    @Test
    void updateUser() {
        UserUpdateRequest userForUpdate = UserUpdateRequest.builder()
                .userId(5L)
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .build();

        Mockito.when(userRepository.findUserById(userForUpdate.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(userMapper.toUserFromUpdateRequest(userForUpdate)).thenReturn(user);
        Mockito.when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse updated = userService.updateUser(userForUpdate);

        assertNotNull(updated);
        assertEquals(userForUpdate.getUserId(), updated.getId());
        assertEquals(userForUpdate.getUserName(), updated.getUserName());
        assertEquals(userForUpdate.getEmail(), updated.getEmail());

        verify(userMapper, times(1)).toUserFromUpdateRequest(userForUpdate);
        verify(userMapper, times(2)).toUserResponse(user);

    }

    @Test
    void getUserById() {
        Mockito.when(userRepository.findUserById(5L)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = userService.getUserById(5L);
        assertNotNull(result);

        verify(userMapper, times(1)).toUserResponse(user);
    }

    @Test
    void deleteUser() {
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userRepository.findUserById(anyLong())).thenReturn(userOptional);

        userService.deleteUser(5L);
        verify(userRepository, times(1)).deleteUserById(user.getId());
    }
}