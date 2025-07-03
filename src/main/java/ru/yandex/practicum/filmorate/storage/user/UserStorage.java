package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {
    Collection<User> getCollectionOfUsers();

    Long createUser(User user);

    boolean updateUser(User user);

    Optional<User> getUserById(long userId);

    boolean addFriend(long userId, long friendId);

    boolean deleteFriend(long userId, long friendId);

    Set<User> getUserFriends(long id);
}