package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> getACollectionOfFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(Long filmId);
}
