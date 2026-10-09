package main.java.com.registro.tecnico.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.registro.tecnico.dto.RegistroUsuarioDTO;
import main.java.com.registro.tecnico.dto.RolDTO;
import main.java.com.registro.tecnico.exception.DataAccessException;
import main.java.com.registro.tecnico.exception.ValidationException;
import main.java.com.registro.tecnico.exception.ViewLoadException;
import main.java.com.registro.tecnico.session.SceneManager;
import main.java.com.registro.tecnico.navigation.View;
import main.java.com.registro.tecnico.service.AuthService;
import main.java.com.registro.tecnico.util.AlertUtil;
import main.java.com.registro.tecnico.util.ErrorHandler;

public class RegistroController {

    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmacion;
    @FXML
    private ComboBox<RolDTO> cmbRol;

    private final AuthService authService;
    private final SceneManager sceneManager;

    public RegistroController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void initialize() {
        try {
            cmbRol.setItems(FXCollections.observableArrayList(authService.listarRoles()));
        } catch (DataAccessException e) {
            ErrorHandler.manejar(e);
        }
    }

    @FXML
    private void registrar() {
        try {
            RolDTO rol = cmbRol.getValue();
            int idRol = rol == null ? 0 : rol.getId();
            authService.registrar(new RegistroUsuarioDTO(
                    txtUsuario.getText(),
                    txtCorreo.getText(),
                    txtPassword.getText(),
                    txtConfirmacion.getText(),
                    idRol));
            AlertUtil.mostrarInformacion("Registro exitoso", "El usuario se registró correctamente");
            sceneManager.navigateTo(View.LOGIN);
        } catch (ValidationException | DataAccessException | ViewLoadException e) {
            ErrorHandler.manejar(e);
        }
    }

    @FXML
    private void volver() {
        try {
            sceneManager.navigateTo(View.LOGIN);
        } catch (ViewLoadException e) {
            ErrorHandler.manejar(e);
        }
    }
}