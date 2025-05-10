package fr.shcherbakov.javafxtexteditor.controller;

import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {

    public BaseController() {
    }

    abstract public void setStage(Stage stage) throws IOException;
}
