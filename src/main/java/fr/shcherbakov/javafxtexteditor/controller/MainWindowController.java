package fr.shcherbakov.javafxtexteditor.controller;

import fr.shcherbakov.javafxtexteditor.service.ViewService;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class MainWindowController extends BaseController {

    @FXML
    private BorderPane root;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TextArea textArea;
    @FXML
    private MenuBarController menuBarController;
    @FXML
    private TextAreaController textAreaController;

    public MainWindowController(ViewService viewService) {
        super(viewService);
    }

    public void initialize() {
        menuBarController.initComponents(this.textArea);
    }
}
