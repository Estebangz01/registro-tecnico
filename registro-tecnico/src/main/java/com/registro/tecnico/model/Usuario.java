package main.java.com.registro.tecnico.model;

public class Usuario {

    private int id;
    private String nombreUsuario;
    private String correo;
    private String passwordHash;
    private Rol rol;

    public Usuario(int id, String nombreUsuario, String correo, String passwordHash, Rol rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.passwordHash = passwordHash;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public Rol getRol() {
        return rol;
    }
}