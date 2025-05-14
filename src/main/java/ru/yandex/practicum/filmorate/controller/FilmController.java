package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {

        LocalDate validReleaseDate = LocalDate.of(1895, 12, 28);

        if (film.getReleaseDate().isBefore(validReleaseDate)) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года.");
        }

        if (film.getDuration().isNegative() || film.getDuration().isZero()) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом.");
        }

        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;

    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {

        if (films.containsKey(newFilm.getId())) {

            Film oldFilm = films.get(newFilm.getId());

            if (!oldFilm.getName().equals(newFilm.getName())) {
                oldFilm.setName(newFilm.getName());
            }
            if (!oldFilm.getDescription().equals(newFilm.getDescription())) {
                oldFilm.setDescription(newFilm.getDescription());
            }
            if (!oldFilm.getReleaseDate().equals(newFilm.getReleaseDate())) {
                oldFilm.setReleaseDate(newFilm.getReleaseDate());
            }
            if (!oldFilm.getDuration().equals(newFilm.getDuration())) {
                oldFilm.setDuration(newFilm.getDuration());
            }

            return oldFilm;
        }

        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден.");

    }

    // Вспомогательный метод для генерации идентификатора нового пользователя
    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;

    }

}
