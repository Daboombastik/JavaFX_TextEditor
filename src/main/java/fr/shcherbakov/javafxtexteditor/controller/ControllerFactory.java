package fr.shcherbakov.javafxtexteditor.controller;

import fr.shcherbakov.javafxtexteditor.config.Injectable;
import fr.shcherbakov.javafxtexteditor.service.ViewService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Injectable
public class ControllerFactory {

    private final Map<Class<?>, Supplier<BaseController>> controllerSuppliers = new HashMap<>();

//    public ControllerFactory() {
//        var container = DIContainer.getContainer();
//        controllerSuppliers.put(MainWindowController.class,
//                () -> container.getInstance(MainWindowController.class));
//        controllerSuppliers.put(MenuBarController.class,
//                () -> container.getInstance(MenuBarController.class));
//        controllerSuppliers.put(TextAreaController.class,
//                () -> container.getInstance(TextAreaController.class));
//    }

    public ControllerFactory(ViewService viewService) {
        controllerSuppliers.put(MainWindowController.class,
                () -> new MainWindowController(viewService));
        controllerSuppliers.put(MenuBarController.class,
                () -> new MenuBarController(viewService));
        controllerSuppliers.put(TextAreaController.class,
                () -> new TextAreaController(viewService));
        controllerSuppliers.put(ExtensionController.class,
                () -> new ExtensionController(viewService));
    }

    public BaseController getController(Class<?> clazz) {
        Supplier<BaseController> supplier = controllerSuppliers.get(clazz);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown controller class: " + clazz);
        }
        return supplier.get();
    }
}