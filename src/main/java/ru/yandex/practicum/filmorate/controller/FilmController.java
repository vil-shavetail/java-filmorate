package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final InMemoryFilmStorage inMemoryFilmStorage;

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
