package main.java.com.registro.tecnico.config;

import main.java.com.registro.tecnico.exception.DataAccessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    Credentials.getUrl(),
                    Credentials.getUser(),
                    Credentials.getPassword());
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo conectar con la base de datos", e);
        }
    }
}