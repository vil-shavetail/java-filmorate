package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserTests {

    private final InMemoryUserStorage userStorage = new InMemoryUserStorage();

    @Test
    void testValidateModelWithValidUser() {
        Assertions.assertDoesNotThrow(() -> {
            User user = new User();
            user.setEmail("mail@mail.com");
            user.setLogin("dolore");
            user.setName("Nick Name");
            user.setBirthday(LocalDate.of(1946, 8, 20));
        }, "Пользователь, соответсвующий условиям валидации, не должен генерировать исключения.");

    }

    @Test
    void testValidateModelUserWithInvalidEmail() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            User user = new User();
            user.setEmail(null);
            user.setLogin("dolore");
            user.setName("dolore ullmaco");
            user.setBirthday(LocalDate.of(1980, 8, 20));
            userStorage.validateUser(user);
        });

        String expectedMessage = "Имейл не может быть пустым.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testValidateModelUserWithInvalidLogin() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            User user = new User();
            user.setEmail("dolore@mail.ru");
            user.setLogin("dolore ullmaco");
            user.setName("dolore");
            user.setBirthday(LocalDate.of(1965, 12, 31));
            userStorage.validateUser(user);
        });

        String expectedMessage = "Логин не может быть пустым.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testValidateModelUserWithFutureBirthday() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            User user = new User();
            user.setEmail("dolore@mail.ru");
            user.setLogin("dolore");
            user.setBirthday(LocalDate.of(2446, 8, 20));
            userStorage.validateUser(user);
        });

        String expectedMessage = "Дата рождения не может быть в будущем.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
