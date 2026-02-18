package com.example.util;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserUpdateRequest;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Log4j2
public class UserValidator {
    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static void validateUserRequest(UserRequest user) {
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder("Ошибки валидации:\n");
            for (ConstraintViolation<UserRequest> v : violations) {
                errors.append(v.getMessage()).append("\n");
            }
            log.error(errors.toString());
            throw new IllegalArgumentException(errors.toString());
        }
    }

    public static void validateUserUpdateRequest(UserUpdateRequest user) {
        Set<ConstraintViolation<UserUpdateRequest>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder errors = new StringBuilder("Ошибки валидации:\n");
            for (ConstraintViolation<UserUpdateRequest> v : violations) {
                errors.append(v.getMessage()).append("\n");
            }
            log.error(errors.toString());
            throw new IllegalArgumentException(errors.toString());
        }
    }

}

