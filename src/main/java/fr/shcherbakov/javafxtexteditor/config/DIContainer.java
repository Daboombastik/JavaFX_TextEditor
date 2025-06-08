package fr.shcherbakov.javafxtexteditor.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * DI Container class
 */
public class DIContainer {
    private static final DIContainer INSTANCE = new DIContainer();
    private final Map<Class<?>, Object> instances = new HashMap<>();

    private DIContainer() {
    }

    public static DIContainer getContainer() {
        return INSTANCE;
    }

    public <T> void register(Class<T> clazz, T instance) {
        instances.put(clazz, instance);
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> clazz) {
        if (instances.containsKey(clazz)) {
            return (T) instances.get(clazz);
        }

        try {
            Constructor<?> constructor = null;
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();

            // Find a constructor with @Inject annotation otherwise take the first one available
            for (Constructor<?> c : constructors) {
                if (c.isAnnotationPresent(Inject.class)) {
                    constructor = c;
                    break;
                }
            }

            if (constructor == null && constructors.length > 0) {
                constructor = constructors[0];
            }

            if (constructor == null) {
                throw new IllegalStateException("Constructor not found " + clazz.getName());
            }

            // Find arguments for a constructor
            Object[] args = new Object[constructor.getParameterCount()];
            Class<?>[] paramTypes = constructor.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                args[i] = getInstance(paramTypes[i]);
            }

            // Instantiate the class with the arguments
            constructor.setAccessible(true);
            T instance = (T) constructor.newInstance(args);
            instances.put(clazz, instance);

            // Inject fields with @Inject annotation
            injectFields(instance);

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("DI error", e.getCause() != null ? e.getCause() : e);
        }
    }

    /**
     * Inject fields annotated with @Inject annotation
     *
     * @param instance - instance
     * @throws IllegalAccessException - thrown if the class or its default constructor is not accessible
     */
    private void injectFields(Object instance) throws IllegalAccessException {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);
                    Object dependency = getInstance(field.getType());
                    field.set(instance, dependency);
                }
            }
            clazz = clazz.getSuperclass(); // Vérifier aussi les champs des classes parentes
        }
    }

    public void injectInto(Object instance) {
        try {
            injectFields(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Erreur lors de l'injection des dépendances", e);
        }
    }
}

/**
 * FXMLLoader personnalisé qui injecte les dépendances dans les contrôleurs
 */
class DIFXMLLoader {
    private final DIContainer container;

    public DIFXMLLoader() {
        this.container = DIContainer.getContainer();
    }

    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Object controller = loader.getController();
        container.injectInto(controller);

        return root;
    }
}

/**
 * Exemple d'utilisation
 */
class ExampleUsage {
    public static void main(String[] args) {
        DIContainer container = DIContainer.getContainer();

        // Enregistrer des implémentations
        container.register(MessageService.class, new SimpleMessageService());
        container.register(LogService.class, new ConsoleLogService());

        // Créer l'application avec injection de dépendances
        Application app = container.getInstance(Application.class);
        app.run();
    }
}

interface MessageService {
    String getMessage();
}

class SimpleMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "Bonjour du service de message!";
    }
}

interface LogService {
    void log(String message);
}

class ConsoleLogService implements LogService {
    @Override
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

class UserService {
    @Inject
    private LogService logService;

    public void performAction() {
        logService.log("Action effectuée par UserService");
    }
}

class Application {
    private final MessageService messageService;
    @Inject
    private UserService userService;

    public Application(MessageService messageService) {
        this.messageService = messageService;
    }

    public void run() {
        System.out.println(messageService.getMessage());
        userService.performAction();
    }
}