package main.java.com.registro.tecnico.session;

import main.java.com.registro.tecnico.dto.UsuarioDTO;

public class SessionManager {

    private UsuarioDTO usuarioActual;

    public void iniciar(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
    }

    public void cerrar() {
        this.usuarioActual = null;
    }

    public UsuarioDTO getUsuarioActual() {
        return usuarioActual;
    }
}