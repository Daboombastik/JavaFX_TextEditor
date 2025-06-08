package fr.shcherbakov.javafxtexteditor;

import fr.shcherbakov.javafxtexteditor.model.Style;
import fr.shcherbakov.javafxtexteditor.model.View;
import fr.shcherbakov.javafxtexteditor.service.ViewService;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewService viewService = ViewService.getInstance();
        viewService.setStage(stage);
        viewService.configureStage();
        viewService.setScene(View.MAIN_WINDOW, Style.DARK_THEME);
        viewService.showStageAndFocus();
    }
}
