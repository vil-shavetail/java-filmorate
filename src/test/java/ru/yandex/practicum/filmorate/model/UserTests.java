package ru.yandex.practicum.filmorate.model;

import jakarta.validation.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void testValidateModelWithValidUser() {
        User user = new User();
        user.setEmail("mail@mail.com");
        user.setLogin("dolore");
        user.setName("Nick Name");
        user.setBirthday(LocalDate.of(1946, 8, 20));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidateModelUserWithInvalidEmail() {
        User user = new User();
        user.setEmail("mail.ru");
        user.setLogin("dolore");
        user.setName("dolore ullmaco");
        user.setBirthday(LocalDate.of(1980, 8, 20));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testValidateModelUserWithInvalidLogin() {
        User user = new User();
        user.setEmail("mail.ru");
        user.setLogin("dolore ullmaco");
        user.setName("dolore");
        user.setBirthday(LocalDate.of(1965, 12, 31));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testValidateModelUserWithFutureBirthday() {
        User user = new User();
        user.setEmail("dolore@mail.ru");
        user.setLogin("dolore");
        user.setBirthday(LocalDate.of(2446, 8, 20));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
}
