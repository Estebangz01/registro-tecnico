package main.java.com.registro.tecnico.repository;

import main.java.com.registro.tecnico.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository {

    void save(Usuario usuario);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    boolean existsByNombreUsuarioOrCorreo(String nombreUsuario, String correo);
}