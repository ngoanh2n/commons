package com.github.ngoanh2n;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Represent a JVM system property.
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
     * Construct {@link Property}.
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
     * Construct {@link Property}.
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
     * Construct a new {@link Property} of {@link String}.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Property<String> ofString(String name) {
        return new Property<>(name, String.class);
    }

    /**
     * Construct a new {@link Property} of {@link String}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Property<String> ofString(String name, String defaultValue) {
        return new Property<>(name, String.class, defaultValue);
    }

    /**
     * Construct a new {@link Property} of {@link Boolean}.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Property<Boolean> ofBoolean(String name) {
        return new Property<>(name, Boolean.class);
    }

    /**
     * Construct a new {@link Property} of {@link Boolean}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Property<Boolean> ofBoolean(String name, Boolean defaultValue) {
        return new Property<>(name, Boolean.class, defaultValue);
    }

    /**
     * Construct a new {@link Property} of {@link Integer}.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Property<Integer> ofInteger(String name) {
        return new Property<>(name, Integer.class);
    }

    /**
     * Construct a new {@link Property} of {@link Integer}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Property<Integer> ofInteger(String name, int defaultValue) {
        return new Property<>(name, Integer.class, defaultValue);
    }

    /**
     * Construct a new {@link Property} of {@link Long}.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Property<Long> ofLong(String name) {
        return new Property<>(name, Long.class);
    }

    /**
     * Construct a new {@link Property} of {@link Long}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Property<Long> ofLong(String name, long defaultValue) {
        return new Property<>(name, Long.class, defaultValue);
    }

    /**
     * Construct a new {@link Property} of {@link Float}.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Property<Float> ofFloat(String name) {
        return new Property<>(name, Float.class);
    }

    /**
     * Construct a new {@link Property} of {@link Float}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Property<Float> ofFloat(String name, float defaultValue) {
        return new Property<>(name, Float.class, defaultValue);
    }

    /**
     * Construct a new {@link Property} of {@link Double}.
     *
     * @param name The name of the JVM system property.
     * @return This instance.
     */
    public static Property<Double> ofDouble(String name) {
        return new Property<>(name, Double.class);
    }

    /**
     * Construct a new {@link Property} of {@link Double}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return This instance.
     */
    public static Property<Double> ofDouble(String name, double defaultValue) {
        return new Property<>(name, Double.class, defaultValue);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get the name of the JVM system property.
     *
     * @return the JVM system property name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the class type of the JVM system property.
     *
     * @return the class type of JVM system property name.
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * Get the value of the JVM system property.
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
     * Set the JVM system property indicated by the specified key.
     *
     * @param newValue The value of the JVM system property.
     */
    public void setValue(T newValue) {
        value = newValue;
        System.setProperty(name, String.valueOf(newValue));
    }

    /**
     * Remove the JVM system property indicated by the specified key.
     */
    public void clearValue() {
        value = null;
        System.clearProperty(name);
    }

    /**
     * Get the default value of the JVM system property.
     *
     * @return the JVM system property object.
     */
    public T getDefaultValue() {
        return defaultValue;
    }
}
