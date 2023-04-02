package com.github.ngoanh2n;

import java.io.File;
import java.util.Properties;

/**
 * Reads properties file.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class PropsFile {
    private final String resourceName;
    private Properties properties;

    /**
     * Constructs this with a resource.
     *
     * @param name Name of properties file in resources dir.
     */
    public PropsFile(String name) {
        this.resourceName = name;
    }

    /**
     * The value of a key from a property file.
     *
     * @param name The name of a property.
     * @return The property value exists in a property file.
     */
    public synchronized String getProp(String name) {
        return getProp(name, "");
    }

    /**
     * The value of a key from a property file.
     *
     * @param name The name of a property.
     * @param defaultValue The default value of a property.
     * @return The property value exists in a property file.
     */
    public synchronized String getProp(String name, String defaultValue) {
        if (properties == null) {
            try {
                File file = Resource.getFile(resourceName);
                properties = Commons.readProps(file, "UTF-8");
            } catch (RuntimeError ignored) {
                properties = new Properties();
            }
        }
        Prop<String> prop = Prop.string(name);
        if (prop.getValue() != null) {
            return prop.getValue();
        }
        return properties.getProperty(name, defaultValue);
    }
}
