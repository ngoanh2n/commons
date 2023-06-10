package com.github.ngoanh2n;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.Properties;

/**
 * Read properties file.
 *
 * @author ngoanh2n
 */
@ParametersAreNonnullByDefault
public class PropertiesFile {
    private Properties properties;

    /**
     * Construct a new {@link PropertiesFile} by a {@link File}.
     *
     * @param file The properties file to read.
     */
    public PropertiesFile(File file) {
        loadPropertiesFile(file);
    }

    /**
     * Construct a new {@link PropertiesFile} by a resource name.
     *
     * @param name The properties file name in resources dir.
     */
    public PropertiesFile(String name) {
        loadPropertiesFile(name);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get the value of property by name.<br>
     * Priority order: JVM System Property, Properties file.
     *
     * @param name The name of property.
     * @return The value of property as {@link String}.
     */
    public synchronized String getProperty(String name) {
        return getProperty(Property.ofString(name));
    }

    /**
     * Get the value of property by other property.<br>
     * Priority order: JVM System Property, Properties file, Default value.
     *
     * @param <T>      The type of current property.
     * @param property The {@link Property} to refer.
     * @return The value of {@link Property}.
     */
    public synchronized <T> T getProperty(Property<T> property) {
        String name = property.getName();
        Class<T> type = property.getType();
        T value = new Property<>(name, type).getValue();

        if (value == null) {
            String valueStr = properties.getProperty(name);
            if (valueStr == null) {
                return property.getDefaultValue();
            } else {
                return Commons.convertValue(type, valueStr);
            }
        }
        return value;
    }

    //-------------------------------------------------------------------------------//

    private void loadPropertiesFile(String name) {
        try {
            File file = Resources.getFile(name);
            loadPropertiesFile(file);
        } catch (RuntimeError ignored) {
            properties = new Properties();
        }
    }

    private void loadPropertiesFile(File file) {
        if (properties == null) {
            try {
                properties = Commons.readProps(file, "UTF-8");
            } catch (RuntimeError ignored) {
                properties = new Properties();
            }
        }
    }
}
