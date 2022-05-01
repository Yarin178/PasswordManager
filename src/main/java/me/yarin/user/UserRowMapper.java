package me.yarin.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserRowMapper implements RowMapper<User> {

    // Dit laat ons meteen een List<User> terug krijgen van de database, i.p.v er zelf over te loopen.
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getString("email"), rs.getString("password"), rs.getInt("userID"));
    }
}
