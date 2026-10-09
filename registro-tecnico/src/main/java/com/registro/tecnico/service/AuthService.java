package main.java.com.registro.tecnico.service;

import main.java.com.registro.tecnico.dto.LoginDTO;
import main.java.com.registro.tecnico.dto.RegistroUsuarioDTO;
import main.java.com.registro.tecnico.dto.RolDTO;
import main.java.com.registro.tecnico.dto.UsuarioDTO;
import java.util.List;

public interface AuthService {

    UsuarioDTO login(LoginDTO loginDTO);

    void registrar(RegistroUsuarioDTO registroDTO);

    List<RolDTO> listarRoles();
}