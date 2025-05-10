package fr.shcherbakov.javafxtexteditor.service;

import fr.shcherbakov.javafxtexteditor.controller.BaseController;
import fr.shcherbakov.javafxtexteditor.controller.MainWindowController;
import fr.shcherbakov.javafxtexteditor.model.ControllerType;
import fr.shcherbakov.javafxtexteditor.model.ViewData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import java.util.Map;
import java.util.stream.Collectors;

public class ViewService {

    private Stage stage;

    private final Map<String, ViewData> viewsAndControllers;


    public ViewService(Stage stage) throws IOException, URISyntaxException {
        setStage(stage);
        this.viewsAndControllers = loadViewsAndControllers();
    }

    public ViewData getViewAndController(String controllerName) {
        return this.viewsAndControllers.get(controllerName);
    }

    public void show(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        ViewData viewData = this.viewsAndControllers.get(name);
        loader.setControllerFactory(param -> viewData.controller());
        Scene scene = new Scene(viewData.view(), 600, 400);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/root.css")).toExternalForm());
        this.stage.setScene(scene);
        viewData.controller().setStage(this.stage);

//        MainWindowController controller = loader.getController();
//        controller.setStage(this.stage);
        this.stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Simple Text Editor");
        this.stage.initStyle(StageStyle.UNDECORATED);
    }

    public Map<String, ViewData> loadViewsAndControllers() throws IOException, URISyntaxException {
        return Files.walk(Path.of(Objects.requireNonNull(getClass().getResource(String.valueOf(Path.of("/views")))).toURI()))
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().contains(".fxml"))
                .map(path -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(path.toUri().toURL());
                        Parent node = loader.load();
                        BaseController controller = loader.getController();
                        return new ViewData(
                                node,
                                controller);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).
                collect(Collectors.toMap(viewData -> viewData.controller().getClass().getSimpleName(), viewData -> viewData));
    }

    public Stage getStage() {
        return this.stage;
    }

    public void close() {
        this.stage.close();
    }

    public void hide() {
        this.stage.hide();
    }

    public void showAndFocus() {
        this.stage.show();
        this.stage.toFront();
    }

    public void minimize() {
        this.stage.setIconified(true);
    }

    public void maximize() {
        this.stage.setMaximized(true);
    }
}
