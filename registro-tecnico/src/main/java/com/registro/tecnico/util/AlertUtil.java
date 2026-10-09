package main.java.com.registro.tecnico.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public final class AlertUtil {

    private AlertUtil() {
    }

    public static void mostrarError(String titulo, String mensaje) {
        crear(AlertType.ERROR, titulo, mensaje).showAndWait();
    }

    public static void mostrarAdvertencia(String titulo, String mensaje) {
        crear(AlertType.WARNING, titulo, mensaje).showAndWait();
    }

    public static void mostrarInformacion(String titulo, String mensaje) {
        crear(AlertType.INFORMATION, titulo, mensaje).showAndWait();
    }

    public static boolean confirmar(String titulo, String mensaje) {
        Optional<ButtonType> resultado = crear(AlertType.CONFIRMATION, titulo, mensaje).showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private static Alert crear(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert;
    }
}