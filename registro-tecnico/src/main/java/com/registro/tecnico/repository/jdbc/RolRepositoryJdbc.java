package main.java.com.registro.tecnico.repository.jdbc;

import main.java.com.registro.tecnico.config.DatabaseConnection;
import main.java.com.registro.tecnico.exception.DataAccessException;
import main.java.com.registro.tecnico.model.Rol;
import main.java.com.registro.tecnico.repository.RolRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolRepositoryJdbc implements RolRepository {

    private static final String SELECT_ALL = "SELECT id_rol, nombre FROM rol ORDER BY nombre";
    private static final String SELECT_BY_ID = "SELECT id_rol, nombre FROM rol WHERE id_rol = ?";

    private final DatabaseConnection databaseConnection;

    public RolRepositoryJdbc(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public List<Rol> findAll() {
        List<Rol> roles = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                roles.add(mapRow(resultSet));
            }
            return roles;
        } catch (SQLException e) {
            throw new DataAccessException("No se pudieron consultar los roles", e);
        }
    }

    @Override
    public Optional<Rol> findById(int id) {
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo consultar el rol", e);
        }
    }

    private Rol mapRow(ResultSet resultSet) throws SQLException {
        return new Rol(resultSet.getInt("id_rol"), resultSet.getString("nombre"));
    }
}