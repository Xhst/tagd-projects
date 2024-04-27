package it.uniroma3.tagd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:postgresql://localhost:5432/tagd-project-2";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void reset() {
        try (Connection connection = getConnection()) {
            try (var statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS bank_account");
                statement.execute("CREATE TABLE bank_account (id SERIAL PRIMARY KEY, balance INT)");
                statement.execute("INSERT INTO bank_account (balance) VALUES (1000)");
            }
        } catch (SQLException ignored) { }
    }
}