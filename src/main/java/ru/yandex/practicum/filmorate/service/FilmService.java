package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {
    public static final int FiLMS_COUNT_LIMIT = 10;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLikeToAFilm(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        Optional.ofNullable(user)
                .orElseThrow(() -> new ValidationException("Пользователь с id " + userId + " не найден"));

        if (film.getLikes().contains(userId)) {
            throw new ValidationException("Пользователь уже лайкнул этот фильм");
        }

        film.getLikes().add(userId);
        filmStorage.updateFilm(film);
    }

    public void deleteAFilmLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        Optional.ofNullable(user)
                .orElseThrow(() -> new ValidationException("Пользователь с id " + userId + " не найден"));

        if (!film.getLikes().remove(userId)) {
            throw new ValidationException("Лайк от пользователя " + userId + " не найден для фильма " + filmId);
        }

        filmStorage.updateFilm(film);
    }

    public List<Film> getACollectionOfTenPopularFilms(int count) {
        return filmStorage.getACollectionOfFilms().stream()
                .sorted(Comparator.comparingInt((Film f) -> f.getLikes().size()).reversed())
                .limit(count > 0 ? count : FiLMS_COUNT_LIMIT)
                .collect(Collectors.toList());
    }

    public Collection<Film> getACollectionOfFilms() {
        return filmStorage.getACollectionOfFilms();
    }

    public Film createFilm(Film film) {
        return Optional.ofNullable(filmStorage.createFilm(film))
                .orElseThrow(() -> new FilmNotFoundException("Фильм с id " + film.getId() + " не найден."));
    }

    public Film updateFilm(Film film) {
        return Optional.ofNullable(filmStorage.updateFilm(film))
                .orElseThrow(() -> new FilmNotFoundException("Фильм с id " + film.getId() + " не найден."));
    }

    public Film getFilmById(Long filmId) {
        Film film = filmStorage.getFilmById(filmId);
        return Optional.ofNullable(film)
                .orElseThrow(() -> new UserNotFoundException("Фильм с id " + filmId + " не найден."));
    }

}
