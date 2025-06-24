package fr.shcherbakov.javafxtexteditor.controller;

import fr.shcherbakov.javafxtexteditor.service.ViewService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Font;

public class TextAreaController extends BaseController {
    @FXML public TextArea textArea;

    public TextAreaController(ViewService viewService) {
        super(viewService);
    }

    public void initialize() {
        initComponents();
        addListeners();
    }

    private void initComponents() {
        textArea.setWrapText(true);
        textArea.setFont(Font.font(24));
    }
    public void addListeners() {
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

    public TextArea getTextArea() {
        return textArea;
    }
}
