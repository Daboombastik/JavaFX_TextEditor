package fr.shcherbakov.javafxtexteditor.controller;

import fr.shcherbakov.javafxtexteditor.model.Extension;
import fr.shcherbakov.javafxtexteditor.service.ViewService;

import java.util.List;

public class ExtensionController extends BaseController {
    public ExtensionController(ViewService viewService) {
        super(viewService);
    }

    public List<Extension> getExtensionList() {
        return List.of(
                new Extension("JsonViewer", ""),
                new Extension("EpubReader", ""),
                new Extension("",""));
    }
}
