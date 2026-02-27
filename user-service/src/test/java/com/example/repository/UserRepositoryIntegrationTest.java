package com.example.repository;

import com.example.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class UserRepositoryIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("testdb")
        .withUsername("testuser")
        .withPassword("testpass");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.liquibase.enabled", ()-> "false");
    }

    @Autowired
    private UserRepository userRepo;


    @Test
    void createUser() {
        User user = User.builder()
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0))
                .build();

        assertNull(user.getId());

        User created = userRepo.save(user);
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

        userRepo.save(user);

        User userForUpdate = User.builder()
                .id(user.getId())
                .userName("NewUser")
                .email("NewUser@domain.com")
                .age(28)
                .build();

        User updated = userRepo.save(userForUpdate);

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

        User created = userRepo.save(user);
        assertNotNull(created);

        Optional<User> found = userRepo.findUserById(created.getId());
        assertNotNull(found.get());
    }

    @Test
    @Transactional
    void deleteUser() {
        User user = User.builder()
                .userName("user")
                .email("email@domain.com")
                .age(20)
                .createdAt(LocalDateTime.of(2020, 1, 1, 0, 0))
                .build();

        User created = userRepo.save(user);
        assertNotNull(created);

        userRepo.deleteUserById(created.getId());
        Optional<User> found = userRepo.findUserById(created.getId());
        assertThat(found).isEmpty();
    }

}