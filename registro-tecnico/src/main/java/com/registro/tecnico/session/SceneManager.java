package main.java.com.registro.tecnico.session;

import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.registro.tecnico.exception.ViewLoadException;
import main.java.com.registro.tecnico.navigation.View;

public class SceneManager {

    private final Stage stage;
    private final Map<View, Supplier<?>> controllerFactories = new EnumMap<>(View.class);

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void register(View view, Supplier<?> controllerFactory) {
        controllerFactories.put(view, controllerFactory);
    }

    public void navigateTo(View view) {
        Supplier<?> controllerFactory = controllerFactories.get(view);
        URL location = getClass().getResource(view.getRuta());
        if (controllerFactory == null || location == null) {
            throw new ViewLoadException("No se pudo preparar la vista " + view.getTitulo(), null);
        }
        try {
            FXMLLoader loader = new FXMLLoader(location);
            loader.setControllerFactory(type -> controllerFactory.get());
            Parent root = loader.load();
            mostrar(root, view.getTitulo());
        } catch (IOException e) {
            throw new ViewLoadException("No se pudo cargar la vista " + view.getTitulo(), e);
        }
    }

    private void mostrar(Parent root, String titulo) {
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root));
        } else {
            stage.getScene().setRoot(root);
        }
        stage.setTitle(titulo);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }
}