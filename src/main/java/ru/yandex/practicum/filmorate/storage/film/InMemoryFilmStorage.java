package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@Primary
public class InMemoryFilmStorage implements FilmStorage {

    public static final LocalDate VALID_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Collection<Film> getACollectionOfFilms() {
        log.info("Запрос на получение списка фильмов.");
        return films.values();
    }

    @Override
    public Film createFilm(Film film) {
        log.info("Создание фильма с id = {}.", film.getId());

        validateFilm(film);
        film.setId(getNextId());
        films.put(film.getId(), film);

        log.info("Фильм с id = {} успешно создан.", film.getId());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("Обновление данных фильма с id = {}.", film.getId());

        if (film.getId() == null || !films.containsKey(film.getId())) {
            log.error("Фильм с id = {} не найден", film.getId());
            throw new FilmNotFoundException("Фильм с id = " + film.getId() + " не найден.");
        }

        films.put(film.getId(), film);
        log.info("Фильм с id = {} успешно обновлен", film.getId() + ".");
        return film;
    }

    @Override
    public Film getFilmById(Long filmId) {
        Film film = films.get(filmId);
        return Optional.ofNullable(film)
                .orElseThrow(() -> new UserNotFoundException("Фильм с id " + filmId + " не найден"));
    }


    // Вспомогательный метод для генерации идентификатора нового фильма.
    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    // Вспомогательный метод для валидации даты релиза фильма.
    public static void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(VALID_RELEASE_DATE)) {
            log.error("Ошибка создания фильма с id = {}. Дата релиза фильма раньше 28 декабря 1985 года", film.getId());
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года.");
        }
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания - 200 символов.");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом.");
        }
    }

}
