package main.java.com.registro.tecnico.repository;

import main.java.com.registro.tecnico.model.Rol;
import java.util.List;
import java.util.Optional;

public interface RolRepository {

    List<Rol> findAll();

    Optional<Rol> findById(int id);
}