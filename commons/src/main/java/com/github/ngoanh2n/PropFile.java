package com.github.ngoanh2n;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.Properties;

/**
 * Reads properties file.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@ParametersAreNonnullByDefault
public class PropFile {
    private Properties props;

    /**
     * Constructs this with a resource name.
     *
     * @param name Name of properties file in resources dir.
     */
    public PropFile(String name) {
        loadPropFile(name);
    }

    /**
     * Constructs this with a {@link File}.
     *
     * @param file Properties file in resources dir.
     */
    public PropFile(File file) {
        loadPropFile(file);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get property value.<br>
     * Priority order: JVM System Property, Properties file.
     *
     * @param name The name of a property.
     * @return The value of a property as {@link String}.
     */
    public synchronized String getPropValue(String name) {
        return getPropValue(Property.string(name));
    }

    /**
     * Get property object.<br>
     * Priority order: JVM System Property, Properties file, Default value.
     *
     * @param <T>  The type of current property.
     * @param property The {@link Property} object.
     * @return The value of {@link Property}.
     */
    public synchronized <T> T getPropValue(Property<T> property) {
        String name = property.getName();
        Class<T> type = property.getType();
        T value = new Property<>(name, type).getValue();

        if (value == null) {
            String valueStr = props.getProperty(name);
            if (valueStr == null) {
                return property.getDefaultValue();
            } else {
                return Commons.convertValue(type, valueStr);
            }
        }
        return value;
    }

    //-------------------------------------------------------------------------------//

    private void loadPropFile(String name) {
        try {
            File file = Resource.getFile(name);
            loadPropFile(file);
        } catch (RuntimeError ignored) {
            props = new Properties();
        }
    }

    private void loadPropFile(File file) {
        if (props == null) {
            try {
                props = Commons.readProps(file, "UTF-8");
            } catch (RuntimeError ignored) {
                props = new Properties();
            }
        }
    }
}
