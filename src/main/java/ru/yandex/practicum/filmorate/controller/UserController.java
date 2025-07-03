package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequestDTO;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequestDTO;
import ru.yandex.practicum.filmorate.dto.user.UserResponseDTO;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<UserResponseDTO> getCollectionOfUsers() {
        return userService.getCollectionOfUsers();
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid NewUserRequestDTO user) {
        return userService.createUser(user);
    }

    @PutMapping
    public UserResponseDTO updateUser(@Valid @RequestBody UpdateUserRequestDTO user) {
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<UserResponseDTO> getFriends(@PathVariable Long id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<UserResponseDTO> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getUserCommonFriends(id, otherId);
    }

}
