package main.java.com.registro.tecnico.dto;

public class UsuarioDTO {

    private final int id;
    private final String nombreUsuario;
    private final String correo;
    private final String rol;

    public UsuarioDTO(int id, String nombreUsuario, String correo, String rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRol() {
        return rol;
    }
}