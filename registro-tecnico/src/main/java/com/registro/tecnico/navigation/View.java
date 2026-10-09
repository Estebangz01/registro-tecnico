package main.java.com.registro.tecnico.navigation;

public enum View {

    LOGIN("/main/resources/view/LoginView.fxml", "Iniciar sesión"),
    REGISTRO("/main/resources/view/RegistroView.fxml", "Registro de usuario"),
    INVENTARIO("/main/resources/view/InventarioView.fxml", "Inventario de aspirantes");

    private final String ruta;
    private final String titulo;

    View(String ruta, String titulo) {
        this.ruta = ruta;
        this.titulo = titulo;
    }

    public String getRuta() {
        return ruta;
    }

    public String getTitulo() {
        return titulo;
    }
}