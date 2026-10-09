package main.java.com.registro.tecnico.repository.jdbc;

import main.java.com.registro.tecnico.config.DatabaseConnection;
import main.java.com.registro.tecnico.exception.DataAccessException;
import main.java.com.registro.tecnico.model.Aspirante;
import main.java.com.registro.tecnico.repository.AspiranteRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AspiranteRepositoryJdbc implements AspiranteRepository {

    private static final String SELECT_ALL =
            "SELECT id_aspirante, nombre, apellido, correo, telefono, direccion, edad "
            + "FROM aspirante ORDER BY id_aspirante";
    private static final String INSERT =
            "INSERT INTO aspirante (nombre, apellido, correo, telefono, direccion, edad) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE aspirante SET nombre = ?, apellido = ?, correo = ?, telefono = ?, "
            + "direccion = ?, edad = ? WHERE id_aspirante = ?";
    private static final String DELETE = "DELETE FROM aspirante WHERE id_aspirante = ?";
    private static final String EXISTS_CORREO =
            "SELECT 1 FROM aspirante WHERE correo = ? AND id_aspirante <> ?";

    private final DatabaseConnection databaseConnection;

    public AspiranteRepositoryJdbc(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public List<Aspirante> findAll() {
        List<Aspirante> aspirantes = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                aspirantes.add(mapRow(resultSet));
            }
            return aspirantes;
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo cargar la lista de aspirantes", e);
        }
    }

    @Override
    public void save(Aspirante aspirante) {
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, aspirante.getNombre());
            statement.setString(2, aspirante.getApellido());
            statement.setString(3, aspirante.getCorreo());
            statement.setString(4, aspirante.getTelefono());
            statement.setString(5, aspirante.getDireccion());
            statement.setInt(6, aspirante.getEdad());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo guardar el aspirante", e);
        }
    }

    @Override
    public void update(Aspirante aspirante) {
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, aspirante.getNombre());
            statement.setString(2, aspirante.getApellido());
            statement.setString(3, aspirante.getCorreo());
            statement.setString(4, aspirante.getTelefono());
            statement.setString(5, aspirante.getDireccion());
            statement.setInt(6, aspirante.getEdad());
            statement.setInt(7, aspirante.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo actualizar el aspirante", e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo eliminar el aspirante", e);
        }
    }

    @Override
    public boolean existsByCorreo(String correo, int excludedId) {
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(EXISTS_CORREO)) {
            statement.setString(1, correo);
            statement.setInt(2, excludedId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo verificar el correo", e);
        }
    }

    private Aspirante mapRow(ResultSet resultSet) throws SQLException {
        return new Aspirante(
                resultSet.getInt("id_aspirante"),
                resultSet.getString("nombre"),
                resultSet.getString("apellido"),
                resultSet.getString("correo"),
                resultSet.getString("telefono"),
                resultSet.getString("direccion"),
                resultSet.getInt("edad"));
    }
}
