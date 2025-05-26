package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final InMemoryUserStorage inMemoryUserStorage;
    private final Map<Long, User> users = new HashMap<>();

    @Autowired
    public UserController(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @GetMapping
    public Collection<User> findAll() {
        return inMemoryUserStorage.getAListOfUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return inMemoryUserStorage.createUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return inMemoryUserStorage.updateUser(user);
    }
}
