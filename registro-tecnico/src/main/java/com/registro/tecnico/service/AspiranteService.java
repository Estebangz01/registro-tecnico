package main.java.com.registro.tecnico.service;

import main.java.com.registro.tecnico.dto.AspiranteDTO;
import java.util.List;

public interface AspiranteService {

    List<AspiranteDTO> listar();

    void agregar(AspiranteDTO aspiranteDTO);

    void actualizar(AspiranteDTO aspiranteDTO);

    void eliminar(int id);
}