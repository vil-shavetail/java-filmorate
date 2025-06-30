package ru.yandex.practicum.filmorate.mapper.film;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRate;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MpaRateRowMapper implements RowMapper<MpaRate> {

    @Override
    public MpaRate mapRow(ResultSet rs, int rowNum) throws SQLException {

        return MpaRate.builder()
                .id(rs.getInt("MPA_RATE_ID"))
                .name(rs.getString("MPA_RATE_NAME"))
//                .description(rs.getString("MPA_RATE_DESCRIPTION"))
                .build();
    }
}
