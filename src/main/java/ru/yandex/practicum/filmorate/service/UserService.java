package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addToFriends(Long userId, Long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

   public void deleteFromFriends(Long userId, Long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public List<User> getACollectionOfFriends(Long userId) {
        User user = userStorage.getUserById(userId);

        return user.getFriends().stream().map(this::getUserById).collect(Collectors.toList());

    }

    public List<User> getACollectionOfMutualFriends(Long userId, Long friendId) {
        User user = getUserById(userId);
        User otherUser = getUserById(friendId);

        Set<Long> commonIds = new HashSet<>(user.getFriends());
        commonIds.retainAll(otherUser.getFriends());

        return commonIds.stream()
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        User user = userStorage.getUserById(id);
        return Optional.ofNullable(user)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + " не найден"));
    }

}
