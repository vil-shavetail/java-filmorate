package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRate;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Qualifier("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();
    private Long filmId = 1L;

    @Override
    public Collection<Film> getACollectionOfFilms() {
        return films.values();
    }

    @Override
    public Long createFilm(Film film) {
        film.setId(filmId++);
        film.setLikes(new ArrayList<>());
        films.put(film.getId(), film);
        return film.getId();
    }

    @Override
    public boolean updateFilm(Film film) {
        if (film.getLikes() == null) {
            film.setLikes(new ArrayList<>());
        }
        films.put(film.getId(), film);
        return true;
    }

    @Override
    public Optional<Film> getFilmById(long id) {
        if (films.containsKey(id)) {
            return Optional.of(films.get(id));
        }
        return Optional.empty();
    }

    @Override
    public int getFilmsCount() {
        return films.size();
    }

    @Override
    public int addUserLike(long filmId, long userId) {
        films.get(filmId).getLikes().add(userId);
        return 1;
    }

    @Override
    public boolean deleteUserLike(long filmId, long userId) {
        films.get(filmId).getLikes().remove(userId);
        return true;
    }

    @Override
    public Collection<Film> getTopFilms(int count) {
        return films.values()
                .stream()
                .sorted(Comparator.comparingInt((Film film) -> film.getLikes().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MpaRate> getMpaRateById(int mpaId) {
        return Optional.empty();
    }

    @Override
    public Collection<MpaRate> getMpaRates() {
        return List.of();
    }

    @Override
    public Optional<Genre> getGenreById(int mpaId) {
        return Optional.empty();
    }

    @Override
    public Collection<Genre> getGenres() {
        return List.of();
    }
}
