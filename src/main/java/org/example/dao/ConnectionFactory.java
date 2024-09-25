package org.example.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
        Properties dbConfig = new Properties();
        try {
            dbConfig.load(getClass().getClassLoader().getResourceAsStream("db/config.properties"));
            return DriverManager.getConnection(
                    dbConfig.getProperty("url"),
                    dbConfig.getProperty("username"),
                    dbConfig.getProperty("password"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
