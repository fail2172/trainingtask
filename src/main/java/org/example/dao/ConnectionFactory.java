package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static volatile ConnectionFactory INSTANCE;

    public static ConnectionFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (ConnectionFactory.class) {
                if (INSTANCE == null) {
                    return INSTANCE = new ConnectionFactory();
                }
            }
        }
        return INSTANCE;
    }

    private ConnectionFactory() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "sova";
        String password = "12345";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
