package main.java.com.registro.tecnico.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.registro.tecnico.dto.AspiranteDTO;
import main.java.com.registro.tecnico.dto.UsuarioDTO;
import main.java.com.registro.tecnico.exception.DataAccessException;
import main.java.com.registro.tecnico.exception.ValidationException;
import main.java.com.registro.tecnico.exception.ViewLoadException;
import main.java.com.registro.tecnico.session.SceneManager;
import main.java.com.registro.tecnico.navigation.View;
import main.java.com.registro.tecnico.service.AspiranteService;
import main.java.com.registro.tecnico.session.SessionManager;
import main.java.com.registro.tecnico.util.AlertUtil;
import main.java.com.registro.tecnico.util.ErrorHandler;

public class InventarioController {

    @FXML
    private Label lblUsuario;
    @FXML
    private TableView<AspiranteDTO> tblAspirantes;
    @FXML
    private TableColumn<AspiranteDTO, Integer> colId;
    @FXML
    private TableColumn<AspiranteDTO, String> colNombre;
    @FXML
    private TableColumn<AspiranteDTO, String> colApellido;
    @FXML
    private TableColumn<AspiranteDTO, String> colCorreo;
    @FXML
    private TableColumn<AspiranteDTO, String> colTelefono;
    @FXML
    private TableColumn<AspiranteDTO, String> colDireccion;
    @FXML
    private TableColumn<AspiranteDTO, Integer> colEdad;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtEdad;

    private final AspiranteService aspiranteService;
    private final SceneManager sceneManager;
    private final SessionManager sessionManager;

    public InventarioController(AspiranteService aspiranteService, SceneManager sceneManager,
            SessionManager sessionManager) {
        this.aspiranteService = aspiranteService;
        this.sceneManager = sceneManager;
        this.sessionManager = sessionManager;
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        tblAspirantes.getSelectionModel().selectedItemProperty().addListener((observable, anterior, seleccionado) -> {
            if (seleccionado != null) {
                cargarFormulario(seleccionado);
            }
        });
        mostrarUsuarioActual();
        try {
            cargarAspirantes();
        } catch (DataAccessException e) {
            ErrorHandler.manejar(e);
        }
    }

    @FXML
    private void agregar() {
        try {
            aspiranteService.agregar(construirDto(0));
            cargarAspirantes();
            limpiarFormulario();
            AlertUtil.mostrarInformacion("Aspirante agregado", "El aspirante se guardó correctamente");
        } catch (ValidationException | DataAccessException e) {
            ErrorHandler.manejar(e);
        }
    }

    @FXML
    private void actualizar() {
        try {
            aspiranteService.actualizar(construirDto(idSeleccionado()));
            cargarAspirantes();
            limpiarFormulario();
            AlertUtil.mostrarInformacion("Aspirante actualizado", "Los datos se actualizaron correctamente");
        } catch (ValidationException | DataAccessException e) {
            ErrorHandler.manejar(e);
        }
    }

    @FXML
    private void eliminar() {
        try {
            int id = idSeleccionado();
            if (id > 0 && !AlertUtil.confirmar("Eliminar aspirante", "¿Desea eliminar el aspirante seleccionado?")) {
                return;
            }
            aspiranteService.eliminar(id);
            cargarAspirantes();
            limpiarFormulario();
            AlertUtil.mostrarInformacion("Aspirante eliminado", "El aspirante se eliminó correctamente");
        } catch (ValidationException | DataAccessException e) {
            ErrorHandler.manejar(e);
        }
    }

    @FXML
    private void limpiarFormulario() {
        tblAspirantes.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtEdad.clear();
    }

    @FXML
    private void cerrarSesion() {
        try {
            sessionManager.cerrar();
            sceneManager.navigateTo(View.LOGIN);
        } catch (ViewLoadException e) {
            ErrorHandler.manejar(e);
        }
    }

    private void cargarAspirantes() {
        tblAspirantes.setItems(FXCollections.observableArrayList(aspiranteService.listar()));
    }

    private void cargarFormulario(AspiranteDTO aspirante) {
        txtNombre.setText(aspirante.getNombre());
        txtApellido.setText(aspirante.getApellido());
        txtCorreo.setText(aspirante.getCorreo());
        txtTelefono.setText(aspirante.getTelefono());
        txtDireccion.setText(aspirante.getDireccion());
        txtEdad.setText(String.valueOf(aspirante.getEdad()));
    }

    private AspiranteDTO construirDto(int id) {
        return new AspiranteDTO(id, txtNombre.getText(), txtApellido.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtDireccion.getText(), leerEdad());
    }

    private int leerEdad() {
        try {
            return Integer.parseInt(txtEdad.getText().trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("La edad debe ser un número entero");
        }
    }

    private int idSeleccionado() {
        AspiranteDTO seleccionado = tblAspirantes.getSelectionModel().getSelectedItem();
        return seleccionado == null ? 0 : seleccionado.getId();
    }

    private void mostrarUsuarioActual() {
        UsuarioDTO usuario = sessionManager.getUsuarioActual();
        if (usuario != null) {
            lblUsuario.setText(usuario.getNombreUsuario() + " (" + usuario.getRol() + ")");
        }
    }
}