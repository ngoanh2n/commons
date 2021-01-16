package io.github.ngoanh2n;

import java.net.URL;

/**
 * Class for representing a system property
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
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

    public String name() {
        return name;
    }

    public Class<T> type() {
        return type;
    }

    public void reset() {
        value = defaultValue;
    }

    @SuppressWarnings("unchecked")
    public T value() {
        String fromSystem = System.getProperty(this.name);
        if (fromSystem == null) return this.value;
        else {
            T result;
            if (type.equals(String.class)) {
                result = (T) fromSystem;
            } else if (type.equals(Integer.class)) {
                result = (T) Integer.valueOf(fromSystem);
            } else if (type.equals(Boolean.class)) {
                result = (T) Boolean.valueOf(fromSystem);
            } else if (type.equals(URL.class)) {
                try {
                    result = (T) new URL(fromSystem);
                } catch (Exception e) {
                    throw new H2nException(e);
                }
            } else {
                throw new H2nException("Type " + type.getTypeName() + " cannot be parsed");
            }
            return result;
        }
    }


    public void value(Object newValue) {
        //noinspection unchecked
        this.value = (T) newValue;
    }
}
