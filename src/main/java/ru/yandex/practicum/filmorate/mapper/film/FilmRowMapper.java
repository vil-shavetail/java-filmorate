package ru.yandex.practicum.filmorate.mapper.film;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRate;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Film.builder()
                .id(rs.getLong("FILM_ID"))
                .name(rs.getString("FILM_NAME"))
                .description(rs.getString("FILM_DESCRIPTION"))
                .releaseDate(rs.getDate("FILM_RELEASE_DATE").toLocalDate())
                .duration(rs.getInt("FILM_DURATION"))
                .mpaRate(MpaRate.builder()
                        .id(rs.getInt("FILM_MPA_RATE"))
                        .build())
                .build();
    }
}