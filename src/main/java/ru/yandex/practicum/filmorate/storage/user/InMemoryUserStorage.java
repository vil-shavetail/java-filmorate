package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Primary
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Collection<User> getCollectionOfUsers() {
        log.info("Запрос на получение списка пользователей.");
        return users.values();
    }

    @Override
    public User createUser(User user) {
        log.info("Создание нового пользователя с id = " + user.getId() + ".");

        validateUser(user);
        user.setId(getNextId());
        users.put(user.getId(), user);

        log.info("Пользователь с id = " + user.getId() + " успешно создан.");
        return user;
    }

    @Override
    public User updateUser(User user) {
        log.info("Обновление данных пользователя с id = {}.", user.getId());

        if (user.getId() == null || !users.containsKey(user.getId())) {
            log.warn("Пользователь с id = {} не найден", user.getId());
            throw new ValidationException("Пользователь с id = " + user.getId() + " не найден.");
        }

        validateUser(user);
        users.put(user.getId(), user);
        log.info("Пользователь с id = {} успешно обновлен", user.getId());
        return user;
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

    private static void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Поскольку имя пользователя не указано, в качестве имени пользователя будет использован логин");
            user.setName(user.getLogin());
        }
    }
}
