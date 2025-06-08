module fr.shcherbakov.javafxtexteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.fxmisc.richtext;
    requires java.desktop;

    opens fr.shcherbakov.javafxtexteditor to javafx.fxml;
    opens fr.shcherbakov.javafxtexteditor.controller to javafx.fxml;
    exports fr.shcherbakov.javafxtexteditor;
    exports fr.shcherbakov.javafxtexteditor.controller;
    exports fr.shcherbakov.javafxtexteditor.service;
    exports fr.shcherbakov.javafxtexteditor.model;
}