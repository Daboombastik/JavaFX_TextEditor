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
                new Extension("JsonViewer", "", null),
                new Extension("EpubReader", "", null),
                new Extension("","", null));
    }

    public Extension useHandler(String name) {
        return switch (name) {
            case "JsonViewer" -> {
                System.out.println("JsonViewer");
                yield new Extension("JsonViewer", null, null);
            }
            case "EpubReader" -> {
                System.out.println("EpubReader");
                yield new Extension("EpubReader", null, null);
            }
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }
}
