package com.example;

import com.example.service.ConsoleService;
import com.example.service.ConsoleServiceImpl;

public class UserServiceApplication {

    public static void main(String[] args) {
        ConsoleService consoleService = new ConsoleServiceImpl();

        consoleService.greeting();
        consoleService.printMenu();
    }
}

