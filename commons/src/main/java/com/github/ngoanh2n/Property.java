package com.github.ngoanh2n;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * Represent a JVM system property.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/commons">ngoanh2n/commons</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/commons">com.github.ngoanh2n:commons</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2019
 */
@ParametersAreNonnullByDefault
public class Property<T> {
    private final String name;
    private final Class<T> type;
    private final T initialValue;
    private T value;
    private final T defaultValue;
    private boolean reassigned;

    //-------------------------------------------------------------------------------//

    /**
     * Construct a new {@link Property}.
     *
     * @param name The name of the JVM system property.
     * @param type The class type of the JVM system property.
     */
    public Property(String name, Class<T> type) {
        this.name = name;
        this.type = type;
        this.value = getValue();
        this.initialValue = value;
        this.defaultValue = value;
        this.reassigned = false;
    }

    /**
     * Construct a new {@link Property}.
     *
     * @param name         The name of the JVM system property.
     * @param type         The class type of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     */
    public Property(String name, Class<T> type, @Nullable T defaultValue) {
        this.name = name;
        this.type = type;
        this.value = getValue();
        this.initialValue = value;
        this.defaultValue = defaultValue;
        this.reassigned = false;
    }

    /**
     * Construct a new {@link Property} of {@link String}.
     *
     * @param name The name of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<String> ofString(String name) {
        return new Property<>(name, String.class);
    }

    /**
     * Construct a new {@link Property} of {@link String}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<String> ofString(String name, String defaultValue) {
        return new Property<>(name, String.class, defaultValue);
    }

    /**
     * Construct a new {@link Property} of {@link Boolean}.
     *
     * @param name The name of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<Boolean> ofBoolean(String name) {
        return new Property<>(name, Boolean.class);
    }

    /**
     * Construct a new {@link Property} of {@link Boolean}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<Boolean> ofBoolean(String name, Boolean defaultValue) {
        return new Property<>(name, Boolean.class, defaultValue);
    }

    /**
     * Construct a new {@link Property} of {@link Integer}.
     *
     * @param name The name of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<Integer> ofInteger(String name) {
        return new Property<>(name, Integer.class);
    }

    /**
     * Construct a new {@link Property} of {@link Integer}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<Integer> ofInteger(String name, int defaultValue) {
        return new Property<>(name, Integer.class, defaultValue);
    }

    /**
     * Construct a new {@link Property} of {@link Long}.
     *
     * @param name The name of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<Long> ofLong(String name) {
        return new Property<>(name, Long.class);
    }

    /**
     * Construct a new {@link Property} of {@link Long}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<Long> ofLong(String name, long defaultValue) {
        return new Property<>(name, Long.class, defaultValue);
    }

    /**
     * Construct a new {@link Property} of {@link Float}.
     *
     * @param name The name of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<Float> ofFloat(String name) {
        return new Property<>(name, Float.class);
    }

    /**
     * Construct a new {@link Property} of {@link Float}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<Float> ofFloat(String name, float defaultValue) {
        return new Property<>(name, Float.class, defaultValue);
    }

    /**
     * Construct a new {@link Property} of {@link Double}.
     *
     * @param name The name of the JVM system property.
     * @return The {@link Property}.
     */
    public static Property<Double> ofDouble(String name) {
        return new Property<>(name, Double.class);
    }

    /**
     * Construct a new {@link Property} of {@link Double}.
     *
     * @param name         The name of the JVM system property.
     * @param defaultValue The default value of the JVM system property.
     * @return The {@link Property}.
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
        String valueInSystem = System.getProperty(name);
        if (valueInSystem == null) {
            if (value != null) {
                return null;
            }
        }
        if (Objects.equals(valueInSystem, "null")) {
            if (value == null) {
                return null;
            }
        }
        if (valueInSystem != null) {
            if (value == null) {
                if (defaultValue != null){
                    return defaultValue;
                }
            }
            return Commons.convertValue(type, valueInSystem);
        }
        return defaultValue;
    }

    /**
     * Set the JVM system property indicated by the specified key.
     *
     * @param newValue The value of the JVM system property.
     */
    public void setValue(@Nullable T newValue) {
        value = newValue;
        reassigned = true;
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

    /**
     * Whether property value was reassigned via {@link #setValue(Object) Property.setValue(newValue)}.
     *
     * @return Indicate property value was reassigned.
     */
    protected boolean isReassigned() {
        T value = getValue();
        if (value != null && initialValue != null) {
            if (value != initialValue) {
                return true;
            }
        }
        return reassigned;
    }
}
