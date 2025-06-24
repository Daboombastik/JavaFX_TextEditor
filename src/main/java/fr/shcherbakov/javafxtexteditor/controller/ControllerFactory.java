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
        ExtensionController extensionController = new ExtensionController(viewService);
        TextAreaController textAreaController = new TextAreaController(viewService);
        MenuBarController menuBarController = new MenuBarController(viewService);
        MainWindowController mainWindowController = new MainWindowController(viewService);

        controllerSuppliers.put(MainWindowController.class,
                () -> mainWindowController);
        controllerSuppliers.put(MenuBarController.class,
                () -> menuBarController);
        controllerSuppliers.put(TextAreaController.class,
                () -> textAreaController);
        controllerSuppliers.put(ExtensionController.class,
                () -> extensionController);
    }

    public BaseController getController(Class<?> clazz) {
        Supplier<BaseController> supplier = controllerSuppliers.get(clazz);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown controller class: " + clazz);
        }
        return supplier.get();
    }
}