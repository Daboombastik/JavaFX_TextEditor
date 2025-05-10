package fr.shcherbakov.javafxtexteditor.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class MainWindowController extends BaseController {

    @FXML private HBox menuBar;
    @FXML private BorderPane root;
    @FXML private TextArea textArea;

    private double xOffset = 0;
    private double yOffset = 0;

    private Stage stage;

    public void setStage(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu-bar.fxml"));
        HBox menuContainer = loader.load();
        root.setTop(menuContainer);
        MenuBarController menuBarController = loader.getController();
        menuBarController.initComponents(this.stage, this.textArea);
//        applyDarkTheme();
    }

    public void initialize() throws IOException {
        // Allow window dragging by the top bar (could also be the MenuBar or HBox)
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            if (stage != null) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        textArea.setWrapText(true);
        // Add font resize on scroll
        textArea.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) {  // Use Ctrl + Scroll for zooming
                double deltaY = event.getDeltaY();
                Font currentFont = textArea.getFont();
                double newSize = currentFont.getSize() + (deltaY > 0 ? 1 : -1);

                // Limit font size
                newSize = Math.max(8, Math.min(newSize, 72));
                textArea.setFont(Font.font(currentFont.getFamily(), newSize));
                textArea.requestLayout();
                textArea.getParent().requestLayout();
                textArea.setVisible(false);
                textArea.setVisible(true);
                event.consume();  // prevent default scroll
            }
        });
    }
}
