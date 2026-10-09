package main.java.com.registro.tecnico.model;

public class Aspirante {

    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private int edad;

    public Aspirante(int id, String nombre, String apellido, String correo,
            String telefono, String direccion, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getEdad() {
        return edad;
    }
}