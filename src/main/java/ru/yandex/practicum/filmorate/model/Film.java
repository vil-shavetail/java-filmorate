package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Film.
 */
@Data
@Builder
public class Film {
    private Long id;
    @NotBlank
    private String name;
    @Size(max = 200, message = "Максимальная длина описания - 200 символов.")
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private  List<Long> likes;
    private MpaRate mpaRate;
    private List<Genre> genres;
}
