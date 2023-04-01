package com.github.ngoanh2n;

import java.net.URL;

/**
 * Class for representing a JVM system property.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@SuppressWarnings("unchecked")
public class Prop<T> {
    private final String name;
    private final Class<T> type;
    private final T defaultValue;
    private T value;

    //-------------------------------------------------------------------------------//

    /**
     * Creates a new Prop.
     *
     * @param name The name of the JVM system property.
     * @param type The class type of the JVM system property.
     */
    public Prop(String name, Class<T> type) {
        this.name = name;
        this.type = type;
        this.value = getValue();
        this.defaultValue = value;
    }

    /**
     * Creates a new Prop.
     *
     * @param name         The name of the JVM system property.
     * @param type         The class type of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     */
    public Prop(String name, Class<T> type, T defaultValue) {
        this.name = name;
        this.type = type;
        this.value = getValue();
        this.defaultValue = defaultValue;
    }

    /**
     * Creates a new string Prop.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Prop<String> string(String name) {
        return new Prop<>(name, String.class);
    }

    /**
     * Creates a new string Prop.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Prop<String> string(String name, String defaultValue) {
        return new Prop<>(name, String.class, defaultValue);
    }

    /**
     * Creates a new boolean Prop.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Prop<Boolean> bool(String name) {
        return new Prop<>(name, Boolean.class);
    }

    /**
     * Creates a new boolean Prop.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Prop<Boolean> bool(String name, Boolean defaultValue) {
        return new Prop<>(name, Boolean.class, defaultValue);
    }

    /**
     * Creates a new integer Prop.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Prop<Integer> integer(String name) {
        return new Prop<>(name, Integer.class);
    }

    /**
     * Creates a new integer Prop.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Prop<Integer> integer(String name, int defaultValue) {
        return new Prop<>(name, Integer.class, defaultValue);
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
        String val = System.getProperty(name);
        if (val == null && value != null) {
            return null;
        }
        if (val != null) {
            if (value == null && defaultValue != null) {
                return defaultValue;
            }
            if (type == URL.class) {
                try {
                    return (T) new URL(val);
                } catch (Exception e) {
                    throw new RuntimeError(e);
                }
            }
            if (type == String.class) return (T) val;
            if (type == Byte.class) return (T) Byte.valueOf(val);
            if (type == Long.class) return (T) Long.valueOf(val);
            if (type == Short.class) return (T) Short.valueOf(val);
            if (type == Float.class) return (T) Float.valueOf(val);
            if (type == Double.class) return (T) Double.valueOf(val);
            if (type == Integer.class) return (T) Integer.valueOf(val);
            if (type == Boolean.class) return (T) Boolean.valueOf(val);
            throw new RuntimeError("Type " + type.getTypeName() + " cannot be parsed");
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
