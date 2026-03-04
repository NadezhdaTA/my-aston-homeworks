package com.example.repository;


import com.example.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    void deleteUserById(Long id);

    Optional<User> findUserById(Long id);

}
