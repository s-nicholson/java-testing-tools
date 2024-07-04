package com.example.testing.db;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple DB client class, using JDBC to avoid cluttering the code
 * under test with persistence library things.
 */
@RequiredArgsConstructor
public class DbClient {
    private final String connectionString;
    private final String user;
    private final String password;

    @SneakyThrows
    public List<User> getUsers() {
        var userList = new ArrayList<User>();
        try (Connection connection = DriverManager.getConnection(connectionString, user, password)) {
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM USERS")
                    .executeQuery();
            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name")));
            }
        }
        return userList;
    }

    public record User(int id, String name) {
    }
}
