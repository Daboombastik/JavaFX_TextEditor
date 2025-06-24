package fr.shcherbakov.javafxtexteditor.controller;

import fr.shcherbakov.javafxtexteditor.config.DIContainer;
import fr.shcherbakov.javafxtexteditor.model.Extension;
import fr.shcherbakov.javafxtexteditor.service.ViewService;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MenuBarController extends BaseController {
    public MenuBar menuBar;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem undoItem;
    @FXML
    private MenuItem redoItem;
    @FXML
    private MenuItem fontMenuItem;
    @FXML
    private CheckMenuItem wrapTextMenuItem;
    @FXML
    private CheckMenuItem darkThemeMenuItem;
    @FXML
    private Menu menuExtension;

    public MenuBarController(ViewService viewService) {
        super(viewService);
    }

    public void initComponents(TextArea textArea) {
        setMenuFile(textArea);
        setMenuEdit(textArea);
        setMenuView(textArea);
        setMenuExtension();
    }

    private void setMenuExtension() {
        ExtensionController extensionController = (ExtensionController) this.getViewService().getControllerFactory().getController(ExtensionController.class);
        List<Extension> extensionList = extensionController.getExtensionList();
        List<MenuItem> extensionMenuItems = extensionList.stream().map(ext -> {
            MenuItem menuItem = new MenuItem(ext.name());
            menuItem.setOnAction(_ -> {
                extensionController.useHandler(ext.name());
            });
            return menuItem;
        }).toList();

        menuExtension.getItems().clear();
        menuExtension.getItems().addAll(extensionMenuItems);
    }

    public void setMenuFile(TextArea textArea) {
        openMenuItem.setOnAction(_ -> openFile(textArea));
        saveMenuItem.setOnAction(_ -> saveFile(textArea));
        exitMenuItem.setOnAction(_ -> {
            if (super.getStage() != null) super.getStage().close();
        });
    }

    public void setMenuEdit(TextArea textArea) {
        undoItem.setOnAction(_ -> textArea.undo());
        redoItem.setOnAction(_ -> textArea.redo());
    }

    public void setMenuView(TextArea textArea) {
        wrapTextMenuItem.setOnAction(_ -> textArea.setWrapText(wrapTextMenuItem.isSelected()));
        darkThemeMenuItem.setOnAction(_ -> toggleTheme());
        fontMenuItem.setOnAction(_ -> showFontDialog(textArea));
    }


    private void openFile(TextArea textArea) {
        textArea.setOpacity(0); // Make invisible before setting text
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        File file = fileChooser.showOpenDialog(super.getStage());
        FadeTransition fadeIn = new FadeTransition(Duration.millis(450), textArea);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.clear();
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());
                fadeIn.play();

            } catch (IOException ex) {
                showAlert("Error reading file", ex.getMessage());
            }
        } else {
            fadeIn.play();
        }
    }

    private void saveFile(TextArea textArea) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Text File");
        File file = fileChooser.showSaveDialog(super.getStage());
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
            } catch (IOException ex) {
                showAlert("Error saving file", ex.getMessage());
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showFontDialog(TextArea textArea) {
        List<String> fonts = Font.getFamilies();
        ChoiceDialog<String> dialog = new ChoiceDialog<>(textArea.getFont().getFamily(), fonts);
        dialog.setTitle("Select Font");
        dialog.setHeaderText("Choose a font:");
        dialog.setContentText("Font:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(font -> textArea.setFont(Font.font(font, textArea.getFont().getSize())));
    }

    private void applyDarkTheme() {
        Scene scene = super.getStage().getScene();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/dark-theme.css")).toExternalForm());
    }

    private void removeDarkTheme() {
        Scene scene = super.getStage().getScene();
        scene.getStylesheets().removeIf(s -> s.contains("dark-theme.css"));
    }

    private void toggleTheme() {
        if (darkThemeMenuItem.isSelected()) {
            applyDarkTheme();
        } else {
            removeDarkTheme();
        }
    }
}
