package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final InMemoryFilmStorage inMemoryFilmStorage;

    public static final LocalDate VALID_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private final Map<Long, Film> films = new HashMap<>();

    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return inMemoryFilmStorage.getAListOfFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }



    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }

}
