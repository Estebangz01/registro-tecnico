package main.java.com.registro.tecnico.repository.jdbc;

import main.java.com.registro.tecnico.config.DatabaseConnection;
import main.java.com.registro.tecnico.exception.DataAccessException;
import main.java.com.registro.tecnico.model.Rol;
import main.java.com.registro.tecnico.model.Usuario;
import main.java.com.registro.tecnico.repository.UsuarioRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioRepositoryJdbc implements UsuarioRepository {

    private static final String INSERT =
            "INSERT INTO usuario (nombre_usuario, correo, password_hash, id_rol) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_NOMBRE =
            "SELECT u.id_usuario, u.nombre_usuario, u.correo, u.password_hash, r.id_rol, r.nombre "
            + "FROM usuario u INNER JOIN rol r ON u.id_rol = r.id_rol WHERE u.nombre_usuario = ?";
    private static final String EXISTS =
            "SELECT 1 FROM usuario WHERE nombre_usuario = ? OR correo = ?";

    private final DatabaseConnection databaseConnection;

    public UsuarioRepositoryJdbc(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void save(Usuario usuario) {
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, usuario.getNombreUsuario());
            statement.setString(2, usuario.getCorreo());
            statement.setString(3, usuario.getPasswordHash());
            statement.setInt(4, usuario.getRol().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo registrar el usuario", e);
        }
    }

    @Override
    public Optional<Usuario> findByNombreUsuario(String nombreUsuario) {
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_NOMBRE)) {
            statement.setString(1, nombreUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapRow(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo consultar el usuario", e);
        }
    }

    @Override
    public boolean existsByNombreUsuarioOrCorreo(String nombreUsuario, String correo) {
        try (Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(EXISTS)) {
            statement.setString(1, nombreUsuario);
            statement.setString(2, correo);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("No se pudo verificar el usuario", e);
        }
    }

    private Usuario mapRow(ResultSet resultSet) throws SQLException {
        Rol rol = new Rol(resultSet.getInt("id_rol"), resultSet.getString("nombre"));
        return new Usuario(
                resultSet.getInt("id_usuario"),
                resultSet.getString("nombre_usuario"),
                resultSet.getString("correo"),
                resultSet.getString("password_hash"),
                rol);
    }
}