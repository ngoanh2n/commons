package com.github.ngoanh2n;

import java.io.File;
import java.util.Properties;

/**
 * Reads properties file.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class PropsFile {
    private final File file;
    private Properties props;

    /**
     * Constructs this with a File.
     *
     * @param file properties file.
     */
    public PropsFile(File file) {
        this.file = file;
    }

    /**
     * Constructs this with a resource.
     *
     * @param resourceName properties resource.
     */
    public PropsFile(String resourceName) {
        this.file = Resource.getFile(resourceName);
    }

    /**
     * The value of a key from a property file.
     *
     * @param propertyName The name of a property.
     * @return The property value exists in a property file.
     */
    public synchronized String getProp(String propertyName) {
        return getProp(propertyName, "");
    }

    /**
     * The value of a key from a property file.
     *
     * @param propertyName The name of a property.
     * @param defaultValue The default value of a property.
     * @return The property value exists in a property file.
     */
    public synchronized String getProp(String propertyName, String defaultValue) {
        if (props == null) {
            props = Commons.readProps(file, "UTF-8");
        }
        return props.getProperty(propertyName, defaultValue);
    }
}
