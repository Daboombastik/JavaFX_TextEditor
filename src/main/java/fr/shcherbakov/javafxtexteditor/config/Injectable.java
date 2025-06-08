package fr.shcherbakov.javafxtexteditor.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation pour marquer les classes qui peuvent être injectées automatiquement
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Injectable {
    /**
     * Spécifie si cette classe doit être un singleton
     */
    boolean singleton() default true;

    /**
     * Spécifie l'interface ou la classe parente que cette classe implémente
     * Si vide, la classe elle-même sera utilisée comme clé d'injection
     */
    Class<?>[] injectAs() default {};
}