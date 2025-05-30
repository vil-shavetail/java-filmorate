package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmTests {

    private final InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();

    @Test
    void testValidateModelValidFilm() {
        Assertions.assertDoesNotThrow(() -> {
            Film film = new Film();
            film.setName("nisi eiusmod");
            film.setDescription("adipisicing");
            film.setReleaseDate(LocalDate.of(2000, 1, 1));
            film.setDuration(100);
            filmStorage.validateFilm(film);
        }, "Фильм, соответсвующий условиям валидации, не должен генерировать исключения.");
    }

    @Test
    void testValidateModelFilmWithEmptyName() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Film film = new Film();
            film.setName(null);
            film.setDescription("Description");
            film.setReleaseDate(LocalDate.of(1900, 3, 25));
            film.setDuration(200);
            InMemoryFilmStorage.validateFilm(film);
        });

        String expectedMessage = "Название фильма не может быть пустым.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testValidateModelFilmWithLongDescription() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Film film = new Film();
            film.setName("Film name");
            film.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят " +
                "разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, " +
                "который за время «своего отсутствия», стал кандидатом Коломбани.");
            film.setReleaseDate(LocalDate.of(1900, 3, 25));
            film.setDuration(200);
            filmStorage.validateFilm(film);
        });

        String expectedMessage = "Максимальная длина описания - 200 символов.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testValidateModelFilmWithInvalidReleaseDate() {
        Exception exception = assertThrows(ValidationException.class, () -> {
            Film film = new Film();
            film.setName("Name");
            film.setDescription("Description");
            film.setReleaseDate(LocalDate.of(1890, 3, 25));
            film.setDuration(200);
            filmStorage.validateFilm(film);
        });

        String expectedMessage = "Дата релиза должна быть не раньше 28 декабря 1895 года.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}
