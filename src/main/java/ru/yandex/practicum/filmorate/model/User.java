package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

/**
 * User.
 */
@Data
public class User {

    private Long id;

    @NotNull(message = "Имейл не должен быть пустым.")
    @NotBlank(message = "Имейл не должен быть пустым.")
    @Email(message = "Имейл должен соответствовать формату: test@domain.ru.")
    private String email;

    @NotNull(message = "Логин не должен быть пустым.")
    @NotBlank(message = "Логин не должен быть пустым.")
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;

}
