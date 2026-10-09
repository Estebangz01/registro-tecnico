package main.java.com.registro.tecnico.dto;

public class RolDTO {

    private final int id;
    private final String nombre;

    public RolDTO(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
