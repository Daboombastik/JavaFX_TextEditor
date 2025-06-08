package fr.shcherbakov.javafxtexteditor.model;

public enum Style {
    DARK_THEME("/styles/dark-theme.css"),
    LIGHT_THEME("/styles/light-theme.css");

    private final String path;

    Style(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
