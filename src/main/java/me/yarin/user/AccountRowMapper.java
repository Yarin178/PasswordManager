package me.yarin.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class AccountRowMapper implements RowMapper<Account> {

    // Dit laat ons meteen een List<Account> terug krijgen van de database, i.p.v er zelf over te loopen.
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Account(rs.getString("accountName"), rs.getString("password"));
    }
}
