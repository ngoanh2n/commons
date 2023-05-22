package com.github.ngoanh2n;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Class for representing a JVM system property.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@ParametersAreNonnullByDefault
public class Property<T> {
    private final String name;
    private final Class<T> type;
    private final T defaultValue;
    private T value;

    //-------------------------------------------------------------------------------//

    /**
     * Creates a new Property.
     *
     * @param name The name of the JVM system property.
     * @param type The class type of the JVM system property.
     */
    public Property(String name, Class<T> type) {
        this.name = name;
        this.type = type;
        this.value = getValue();
        this.defaultValue = value;
    }

    /**
     * Creates a new Property.
     *
     * @param name         The name of the JVM system property.
     * @param type         The class type of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     */
    public Property(String name, Class<T> type, @Nullable T defaultValue) {
        this.name = name;
        this.type = type;
        this.value = getValue();
        this.defaultValue = defaultValue;
    }

    /**
     * Creates a new string Property.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Property<String> string(String name) {
        return new Property<>(name, String.class);
    }

    /**
     * Creates a new string Property.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Property<String> string(String name, String defaultValue) {
        return new Property<>(name, String.class, defaultValue);
    }

    /**
     * Creates a new boolean Property.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Property<Boolean> bool(String name) {
        return new Property<>(name, Boolean.class);
    }

    /**
     * Creates a new boolean Property.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Property<Boolean> bool(String name, Boolean defaultValue) {
        return new Property<>(name, Boolean.class, defaultValue);
    }

    /**
     * Creates a new integer Property.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Property<Integer> integer(String name) {
        return new Property<>(name, Integer.class);
    }

    /**
     * Creates a new integer Property.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Property<Integer> integer(String name, int defaultValue) {
        return new Property<>(name, Integer.class, defaultValue);
    }

    //-------------------------------------------------------------------------------//

    /**
     * The name of the system property.
     *
     * @return the JVM system property name.
     */
    public String getName() {
        return name;
    }

    /**
     * The class type of the JVM system property.
     *
     * @return the class type of JVM system property name.
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * The value of the system property.
     *
     * @return the JVM system property value.
     */
    public T getValue() {
        String valueStr = System.getProperty(name);
        if (valueStr == null && value != null) {
            return null;
        }
        if (valueStr != null) {
            if (value == null && defaultValue != null) {
                return defaultValue;
            }
            return Commons.convertValue(type, valueStr);
        }
        return defaultValue;
    }

    /**
     * Sets the JVM system property indicated by the specified key.
     *
     * @param newValue The value of the JVM system property.
     */
    public void setValue(T newValue) {
        value = newValue;
        System.setProperty(name, String.valueOf(newValue));
    }

    /**
     * Removes the JVM system property indicated by the specified key.
     */
    public void clearValue() {
        value = null;
        System.clearProperty(name);
    }

    /**
     * The default value of the JVM system property.
     *
     * @return the JVM system property object.
     */
    public T getDefaultValue() {
        return defaultValue;
    }
}
