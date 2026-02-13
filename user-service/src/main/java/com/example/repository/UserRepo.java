package com.example.repository;


import com.example.model.entity.User;

import java.util.Optional;

public interface UserRepo {
    User createUser(User user);
    User updateUser(User newUser);
    void deleteUser(User user);
    Optional<User> getUserById(Long userId);

}
