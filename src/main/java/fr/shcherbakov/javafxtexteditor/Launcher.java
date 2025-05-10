package fr.shcherbakov.javafxtexteditor;

import fr.shcherbakov.javafxtexteditor.controller.MainWindowController;
import fr.shcherbakov.javafxtexteditor.service.ViewService;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.desktop.QuitEvent;
import java.awt.desktop.QuitHandler;
import java.awt.desktop.QuitResponse;

import static fr.shcherbakov.javafxtexteditor.model.ControllerType.MAIN_WINDOW;

public class Launcher extends Application {

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupMacOSMenuHandlers();
        new ViewService(primaryStage).show(MAIN_WINDOW.getName());
    }

    private void setupMacOSMenuHandlers() {
        if (!Desktop.isDesktopSupported()) return;

        Desktop desktop = Desktop.getDesktop();

        // About Menu
        desktop.setAboutHandler(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("About Simple Text Editor");
            alert.setContentText("Version 1.0\nCreated by You.");
            alert.showAndWait();
        });

        // Preferences Menu
        desktop.setPreferencesHandler(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Preferences");
            alert.setHeaderText("Preferences");
            alert.setContentText("You can customize editor preferences here.");
            alert.showAndWait();
        });

        // Quit Menu
        desktop.setQuitHandler(new QuitHandler() {
            @Override
            public void handleQuitRequestWith(QuitEvent e, QuitResponse response) {
                // Optional: prompt to save or cancel
                response.performQuit(); // or response.cancelQuit();
            }
        });
    }

}
