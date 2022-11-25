package com.github.ngoanh2n;

import java.net.URL;

/**
 * Class for representing a system property
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@SuppressWarnings("unchecked")
public class Prop<T> {
    private final String name;
    private final Class<T> type;
    private T value;
    private final T defaultValue;

    public Prop(String name, Class<T> type) {
        this.name = name;
        this.type = type;
        this.value = getValue();
        this.defaultValue = value;
    }

    public Prop(String name, Class<T> type, T defaultValue) {
        this.name = name;
        this.type = type;
        this.value = getValue();
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    public T getValue() {
        String val = System.getProperty(name);
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

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setValue(T newValue) {
        value = newValue;
        System.setProperty(name, String.valueOf(newValue));
    }
}
