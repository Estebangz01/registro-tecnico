package main.java.com.registro.tecnico.util;

import main.java.com.registro.tecnico.exception.AuthException;
import main.java.com.registro.tecnico.exception.DataAccessException;
import main.java.com.registro.tecnico.exception.ValidationException;
import main.java.com.registro.tecnico.exception.ViewLoadException;

public final class ErrorHandler {

    private ErrorHandler() {
    }

    public static void manejar(RuntimeException e) {
        if (e instanceof ValidationException) {
            AlertUtil.mostrarAdvertencia("Datos inválidos", e.getMessage());
        } else if (e instanceof AuthException) {
            AlertUtil.mostrarError("Error de autenticación", e.getMessage());
        } else if (e instanceof DataAccessException) {
            AlertUtil.mostrarError("Error de base de datos", e.getMessage());
        } else if (e instanceof ViewLoadException) {
            AlertUtil.mostrarError("Error de interfaz", e.getMessage());
        } else {
            AlertUtil.mostrarError("Error inesperado", "Ocurrió un error inesperado. Intente nuevamente");
        }
    }
}