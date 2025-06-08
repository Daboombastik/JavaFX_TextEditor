package fr.shcherbakov.javafxtexteditor.service;

import fr.shcherbakov.javafxtexteditor.controller.ControllerFactory;
import fr.shcherbakov.javafxtexteditor.model.Style;
import fr.shcherbakov.javafxtexteditor.model.View;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ViewService {

    private Stage stage;
    private final ControllerFactory controllerFactory;
    private static volatile ViewService INSTANCE;

    public static synchronized ViewService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewService();
        }
        return INSTANCE;
    }

    private ViewService() {
        this.controllerFactory = new ControllerFactory(this);
    }

    public void setScene(View view, Style style) throws IOException {
        Parent root = loadView(view);
        Scene scene = createScene(root, style);
        this.stage.setScene(scene);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void configureStage() {
        this.stage.setTitle("Text Editor");
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.7;
        double height = screenBounds.getHeight() * 0.7;
        this.stage.setMinWidth(width);
        this.stage.setMinHeight(height);
    }


    public Parent loadView(View view) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view.getPath()));
        loader.setControllerFactory(controllerFactory::getController);
        return loader.load();
    }

    public Scene createScene(Parent root, Style style) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource(style.getPath())).toExternalForm()
        );
        return scene;
    }

    public void closeStage() {
        this.stage.close();
    }

    public void hideStage() {
        this.stage.hide();
    }

    public void showStageAndFocus() {
        this.stage.show();
        this.stage.toFront();
    }

    public void minimize() {
        this.stage.setIconified(true);
    }

    public void maximize() {
        this.stage.setMaximized(true);
    }

    public ControllerFactory getControllerFactory() {
        return this.controllerFactory;
    }
}
