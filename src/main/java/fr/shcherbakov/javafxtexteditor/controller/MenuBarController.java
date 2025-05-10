package fr.shcherbakov.javafxtexteditor.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MenuBarController extends BaseController {
    private Stage stage;

    @FXML
    private Menu menuEdit;
    @FXML
    private Menu menuView;
    @FXML
    private Menu menuFile;
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
    @FXML private Button closeButton;
    @FXML private Button minimizeButton;

    @Override
    public void setStage(Stage stage) throws IOException {
        this.stage = stage;
    }

    public void initComponents(Stage stage, TextArea textArea) {
        this.stage = stage;
        setMenuFile(stage, textArea);
        setMenuEdit(textArea);
        setMenuView(stage, textArea);
        setButtons(stage);
    }

    public void setMenuFile(Stage stage, TextArea textArea) {
        openMenuItem.setOnAction(e -> openFile(stage, textArea));
        saveMenuItem.setOnAction(e -> saveFile(stage, textArea));
        exitMenuItem.setOnAction(e -> {
            if (stage != null) stage.close();
        });
    }

    public void setMenuEdit(TextArea textArea) {
        undoItem.setOnAction(e -> textArea.undo());
        redoItem.setOnAction(e -> textArea.redo());
    }

    public void setMenuView(Stage stage, TextArea textArea) {
        wrapTextMenuItem.setOnAction(e -> textArea.setWrapText(wrapTextMenuItem.isSelected()));
        darkThemeMenuItem.setOnAction(e -> toggleTheme(stage));
        fontMenuItem.setOnAction(e -> showFontDialog(textArea));
    }

    public void setButtons(Stage stage){
        closeButton.setOnAction(e -> stage.close());
        minimizeButton.setOnAction(e -> stage.setIconified(true));
    }

    private void openFile(Stage stage, TextArea textArea) {
        textArea.setOpacity(0); // Make invisible before setting text
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        File file = fileChooser.showOpenDialog(stage);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), textArea);
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
        }
    }

    private void saveFile(Stage stage, TextArea textArea) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Text File");
        File file = fileChooser.showSaveDialog(stage);
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

    private void applyDarkTheme(Stage stage) {
        Scene scene = stage.getScene();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/dark-theme.css")).toExternalForm());
    }

    private void removeDarkTheme(Stage stage) {
        Scene scene = stage.getScene();
        scene.getStylesheets().removeIf(s -> s.contains("dark-theme.css"));
    }

    private void toggleTheme(Stage stage) {
        if (darkThemeMenuItem.isSelected()) {
            applyDarkTheme(stage);
        } else {
            removeDarkTheme(stage);
        }
    }
}
