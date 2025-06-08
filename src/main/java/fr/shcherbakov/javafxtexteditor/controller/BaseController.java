package fr.shcherbakov.javafxtexteditor.controller;

import fr.shcherbakov.javafxtexteditor.service.ViewService;
import javafx.stage.Stage;

public abstract class BaseController {

    private Stage stage;
    private final ViewService viewService;

    public BaseController(ViewService viewService) {
        this.viewService = viewService;
        this.stage = this.viewService.getStage();
    }

    public ViewService getViewService() {
        return this.viewService;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
