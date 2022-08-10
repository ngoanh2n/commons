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
    private T value;
    private final String name;
    private final Class<T> type;
    private final T defaultValue;

    public Prop(String name, Class<T> type) {
        this.name = name;
        this.type = type;
        this.value = null;
        this.defaultValue = null;
    }

    public Prop(String name, Class<T> type, T defaultValue) {
        this.name = name;
        this.type = type;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    public void resetValue() {
        value = defaultValue;
    }

    public T getValue() {
        String sValue = System.getProperty(name);
        if (sValue == null) {
            return value;
        } else {
            if (type == URL.class) {
                try {
                    return (T) new URL(sValue);
                } catch (Exception e) {
                    throw new RuntimeError(e);
                }
            }
            if (type == Byte.class) return (T) Byte.valueOf(sValue);
            if (type == Long.class) return (T) Long.valueOf(sValue);
            if (type == Short.class) return (T) Short.valueOf(sValue);
            if (type == Float.class) return (T) Float.valueOf(sValue);
            if (type == Double.class) return (T) Double.valueOf(sValue);
            if (type == Integer.class) return (T) Integer.valueOf(sValue);
            if (type == Boolean.class) return (T) Boolean.valueOf(sValue);
            throw new RuntimeError("Type " + type.getTypeName() + " cannot be parsed");
        }
    }

    public void setValue(Object newValue) {
        setValue(newValue, false);
    }

    public void setValue(Object newValue, boolean systemAlso) {
        this.value = (T) newValue;
        if (systemAlso) System.setProperty(name, String.valueOf(newValue));
    }
}
