package com.example.repository;

import com.example.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@Testcontainers
public class UserRepoImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("testdb")
        .withUsername("testuser")
        .withPassword("testpass");

    private UserRepoImpl userRepo;

    @BeforeEach
    void setUp() {
        String jdbcUrl = postgres.getJdbcUrl();
        HibernateUtil.buildSessionFactory(
            "org.postgresql.Driver",
            jdbcUrl,
            "testuser",
            "testpass",
            "org.hibernate.dialect.PostgreSQLDialect",
            "create-drop"
    );

    userRepo = new UserRepoImpl();
    }

    @AfterEach
    void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    void createUser() {
        User user = User.builder()
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0))
                .build();

        assertNull(user.getId());

        User created = userRepo.createUser(user);
        assertNotNull(user.getId());

        assertThat(created)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(user);
    }

    @Test
    void updateUser() {
        User user = User.builder()
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0))
                .build();

        userRepo.createUser(user);

        User userForUpdate = User.builder()
                .id(user.getId())
                .userName("NewUser")
                .email("NewUser@domain.com")
                .age(28)
                .build();

        User updated = userRepo.updateUser(userForUpdate);

        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(userForUpdate);
    }

    @Test
    void getUserById() {
        User user = User.builder()
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0))
                .build();

        User created = userRepo.createUser(user);
        assertNotNull(created);

        Optional<User> found = userRepo.getUserById(created.getId());
        assertNotNull(found.get());
    }

        @Test
    void deleteUser() {
        User user = User.builder()
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0))
                .build();

        User created = userRepo.createUser(user);
        assertNotNull(created);

        userRepo.deleteUser(created);
        Optional<User> found = userRepo.getUserById(created.getId());
        assertThat(found).isEmpty();
    }

}