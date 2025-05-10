package fr.shcherbakov.javafxtexteditor.controller;

import javafx.stage.Stage;

import java.io.IOException;

public class TextAreaController extends BaseController {
    public Stage stage;

    @Override
    public void setStage(Stage stage) throws IOException {
       this.stage = stage;
    }
}
