package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {

        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы.");
        }

        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;

    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {

        if (newUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы.");
        }

        if (newUser.getName().isEmpty()) {
            newUser.setName(newUser.getLogin());
        }

        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());

            if (!oldUser.getName().equals(newUser.getName())) {
                oldUser.setName(newUser.getName());
            }
            if (!oldUser.getBirthday().equals(newUser.getBirthday())) {
                oldUser.setBirthday(newUser.getBirthday());
            }
            if (!oldUser.getLogin().equals(newUser.getLogin())) {
                oldUser.setLogin(newUser.getLogin());
            }
            if (!oldUser.getEmail().equals(newUser.getEmail())) {
                oldUser.setEmail(newUser.getEmail());
            }

            return oldUser;

        }

        throw new ValidationException("Пользователь с id = " + newUser.getId() + " не найден.");

    }

    // Вспомогательный метод для генерации идентификатора нового пользователя
    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;

    }

}
