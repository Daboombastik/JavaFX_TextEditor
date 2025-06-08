package fr.shcherbakov.javafxtexteditor.model;

public enum View {
    MAIN_WINDOW("MainWindowController", "/views/main-window.fxml"),
    MENU_BAR("MenuBarController", "/views/menu-bar.fxml"),
    TEXT_AREA("TextAreaController", "/views/text-area.fxml");

    private final String name;
    private final String path;
    View(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }
}
