package com.github.ngoanh2n;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.Properties;

/**
 * Read properties file.<br><br>
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
public class PropertiesFile {
    private final boolean assignToSystem;
    private final Properties properties;

    /**
     * Construct a new {@link PropertiesFile} by a resource name.
     *
     * @param resourceName The properties file name in resources dir to read.
     */
    public PropertiesFile(String resourceName) {
        this(resourceName, false);
    }

    /**
     * Construct a new {@link PropertiesFile} by a resource name.
     *
     * @param resourceName   The properties file name in resources dir to read.
     * @param assignToSystem Indicate to set all properties in file to system.
     */
    public PropertiesFile(String resourceName, boolean assignToSystem) {
        this.assignToSystem = assignToSystem;
        this.properties = loadPropertiesFromResourceName(resourceName);
    }

    /**
     * Construct a new {@link PropertiesFile} by a {@link File}.
     *
     * @param file The properties file to read.
     */
    public PropertiesFile(File file) {
        this(file, false);
    }

    /**
     * Construct a new {@link PropertiesFile} by a {@link File}.
     *
     * @param file           The properties file to read.
     * @param assignToSystem Indicate to set all properties in file to system.
     */
    public PropertiesFile(File file, boolean assignToSystem) {
        this.assignToSystem = assignToSystem;
        this.properties = loadPropertiesFromFile(file);
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
            if (property.isReassigned()) {
                return null;
            }
            String valueInFile = properties.getProperty(name);
            if (valueInFile == null) {
                return property.getDefaultValue();
            } else {
                return Commons.convertValue(type, valueInFile);
            }
        }
        return value;
    }

    /**
     * Get all properties in this file.
     *
     * @return All properties have been read.
     */
    public synchronized Properties getProperties() {
        return properties;
    }

    //-------------------------------------------------------------------------------//

    private Properties loadPropertiesFromResourceName(String name) {
        Properties properties;
        try {
            File file = Resources.getFile(name);
            properties = loadPropertiesFromFile(file);
        } catch (RuntimeError ignored) {
            properties = new Properties();
        }
        assignPropertiesToSystem(properties);
        return properties;
    }

    private Properties loadPropertiesFromFile(File file) {
        Properties properties;
        try {
            properties = Commons.readProps(file, "UTF-8");
        } catch (RuntimeError ignored) {
            properties = new Properties();
        }
        assignPropertiesToSystem(properties);
        return properties;
    }

    private void assignPropertiesToSystem(Properties properties) {
        if (assignToSystem) {
            properties.forEach((name, value) -> {
                Property<String> property = Property.ofString((String) name);
                if (property.getValue() == null) {
                    property.setValue((String) value);
                }
            });
        }
    }
}
