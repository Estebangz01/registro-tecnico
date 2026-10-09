package main.java.com.registro.tecnico;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.com.registro.tecnico.config.DatabaseConnection;
import main.java.com.registro.tecnico.controller.InventarioController;
import main.java.com.registro.tecnico.controller.LoginController;
import main.java.com.registro.tecnico.controller.RegistroController;
import main.java.com.registro.tecnico.exception.ViewLoadException;
import main.java.com.registro.tecnico.session.SceneManager;
import main.java.com.registro.tecnico.navigation.View;
import main.java.com.registro.tecnico.repository.AspiranteRepository;
import main.java.com.registro.tecnico.repository.RolRepository;
import main.java.com.registro.tecnico.repository.UsuarioRepository;
import main.java.com.registro.tecnico.repository.jdbc.AspiranteRepositoryJdbc;
import main.java.com.registro.tecnico.repository.jdbc.RolRepositoryJdbc;
import main.java.com.registro.tecnico.repository.jdbc.UsuarioRepositoryJdbc;
import main.java.com.registro.tecnico.service.AspiranteService;
import main.java.com.registro.tecnico.service.AspiranteServiceImpl;
import main.java.com.registro.tecnico.service.AuthService;
import main.java.com.registro.tecnico.service.AuthServiceImpl;
import main.java.com.registro.tecnico.session.SessionManager;
import main.java.com.registro.tecnico.util.ErrorHandler;

public class RegistroMain extends Application {

    @Override
    public void start(Stage stage) {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        UsuarioRepository usuarioRepository = new UsuarioRepositoryJdbc(databaseConnection);
        RolRepository rolRepository = new RolRepositoryJdbc(databaseConnection);
        AspiranteRepository aspiranteRepository = new AspiranteRepositoryJdbc(databaseConnection);

        AuthService authService = new AuthServiceImpl(usuarioRepository, rolRepository);
        AspiranteService aspiranteService = new AspiranteServiceImpl(aspiranteRepository);

        SessionManager sessionManager = new SessionManager();
        SceneManager sceneManager = new SceneManager(stage);

        sceneManager.register(View.LOGIN,
                () -> new LoginController(authService, sceneManager, sessionManager));
        sceneManager.register(View.REGISTRO,
                () -> new RegistroController(authService, sceneManager));
        sceneManager.register(View.INVENTARIO,
                () -> new InventarioController(aspiranteService, sceneManager, sessionManager));

        try {
            sceneManager.navigateTo(View.LOGIN);
        } catch (ViewLoadException e) {
            ErrorHandler.manejar(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}