package ru.yandex.practicum.filmorate.mapper.user;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        return User.builder()
                .id(rs.getLong("USER_ID"))
                .email(rs.getString("USER_EMAIL"))
                .login(rs.getString("USER_LOGIN"))
                .name(rs.getString("USER_NAME"))
                .birthday(rs.getDate("USER_BIRTHDAY").toLocalDate())
                .friends(new HashSet<>())
                .build();
    }
}
