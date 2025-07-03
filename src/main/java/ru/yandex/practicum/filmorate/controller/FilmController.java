package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.film.FilmResponseDTO;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequestDTO;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequestDTO;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Collection<FilmResponseDTO> getACollectionOfFilms() {
        return filmService.getACollectionOfFilms();
    }

    @PostMapping
    public FilmResponseDTO createFilm(@RequestBody @Valid NewFilmRequestDTO newFilmDTO) {
        return filmService.createFilm(newFilmDTO);
    }

    @PutMapping
    public FilmResponseDTO updateFilm(@RequestBody @Valid UpdateFilmRequestDTO filmToUpdate) {
        return filmService.updateFilm(filmToUpdate);
    }

    @GetMapping("/{filmId}")
    public FilmResponseDTO getFilmById(@PathVariable long filmId) {
        return filmService.getFilmById(filmId);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addUserLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.addUserLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteUserLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.deleteUserLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<FilmResponseDTO> getTopFilms(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getTopFilms(count);
    }

}
