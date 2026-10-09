package main.java.com.registro.tecnico.dto;

public class RegistroUsuarioDTO {

    private final String nombreUsuario;
    private final String correo;
    private final String password;
    private final String confirmacionPassword;
    private final int idRol;

    public RegistroUsuarioDTO(String nombreUsuario, String correo, String password,
            String confirmacionPassword, int idRol) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.password = password;
        this.confirmacionPassword = confirmacionPassword;
        this.idRol = idRol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmacionPassword() {
        return confirmacionPassword;
    }

    public int getIdRol() {
        return idRol;
    }
}