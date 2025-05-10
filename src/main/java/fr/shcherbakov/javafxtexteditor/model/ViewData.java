package fr.shcherbakov.javafxtexteditor.model;

import fr.shcherbakov.javafxtexteditor.controller.BaseController;
import javafx.scene.Parent;

public record ViewData(Parent view, BaseController controller) {
}
