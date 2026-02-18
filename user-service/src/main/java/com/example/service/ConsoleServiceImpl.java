package com.example.service;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.dto.UserUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class ConsoleServiceImpl implements ConsoleService {
    private static final Logger log = LogManager.getLogger(ConsoleServiceImpl.class);
    private final UserService userService = new UserServiceImpl();
    private final Scanner scanner = new Scanner(System.in);


    @Override
    public void greeting() {
        System.out.println("Добро пожаловать в сервис регистрации пользователей!");
    }

    @Override
    public void printMenu() {
        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Получить пользователя по ID");
            System.out.println("3. Обновить пользователя");
            System.out.println("4. Удалить пользователя");
            System.out.println("5. Выход");
            System.out.print("Выберите действие (1–5): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createUser();
                case 2 -> getUserById();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 5 -> {
                    System.out.println("Завершение работы...");
                    return;
                }
                default -> System.out.println("Ошибка ввода: такого варианта ответа нет\"");
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class InputObject {
        private String userName;
        private String email;
        private Integer age;
    }

    private InputObject printRequest() {
        System.out.println("\nВведите имя пользователя: ");
        String userName = scanner.nextLine();

        System.out.println("\nВведите email пользователя: ");
        String email = scanner.nextLine();

        System.out.println("\nВведите возраст пользователя: ");
        String age = scanner.nextLine();
        Integer ageInt = null;
        if (!age.isEmpty()) {
            ageInt = Integer.parseInt(age);
        }

        return InputObject.builder()
                .userName(userName)
                .email(email)
                .age(ageInt)
                .build();
    }

    private void createUser() {
        try {
            InputObject input = printRequest();

            UserRequest userRequest = UserRequest.builder()
                    .userName(input.getUserName())
                    .email(input.getEmail())
                    .age(input.getAge())
                    .build();

            UserResponse userResponse = userService.createUser(userRequest);

            System.out.println("\nПользователь создан:");
            System.out.println(userResponse);
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            log.error(e);
        } finally {
            printMenu();
        }

    }

    private void getUserById() {
        try {
            System.out.println("Введите ID пользователя, которого хотите найти: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            UserResponse userResponse = userService.getUserById(id);

            System.out.println("\nПользователь найден: ");
            System.out.println(userResponse);
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            log.error(e);
        } finally {
            printMenu();
        }

    }

    private void updateUser() {
        try {
            System.out.println("Введите ID пользователя, которого хотите изменить: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            System.out.println("Введите новые данные пользователя");

            InputObject input = printRequest();
            UserUpdateRequest userRequest = UserUpdateRequest.builder()
                    .userId(id)
                    .userName(input.getUserName())
                    .email(input.getEmail())
                    .age(input.getAge())
                    .build();

            UserResponse userResponse = userService.updateUser(userRequest);

            System.out.println("\nПользователь обновлен: ");
            System.out.println(userResponse);
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            log.error(e);
        } finally {
            printMenu();
        }
    }

    private void deleteUser() {
        System.out.println("Введите ID пользователя, которого хотите удалить: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        try {
            userService.deleteUser(id);
            System.out.println("\nПользователь удален. ");
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            log.error(e);
        } finally {
            printMenu();
        }

    }
}
