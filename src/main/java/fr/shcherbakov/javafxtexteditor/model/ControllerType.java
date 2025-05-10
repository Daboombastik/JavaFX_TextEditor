package fr.shcherbakov.javafxtexteditor.model;

public enum ControllerType {
    MAIN_WINDOW("MainWindowController"),
    MENU_BAR("MenuBarController"),
    TEXT_AREA("TextAreaController");

    private String name;
    ControllerType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
