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

    public PropsFile(File file) {
        this.file = file;
    }

    public PropsFile(String resourceName) {
        this.file = Resource.getFile(resourceName);
    }

    public synchronized String getProp(String key) {
        if (props == null) {
            props = Commons.readProps(file, "UTF-8");
        }
        return getProp(key, "");
    }

    public synchronized String getProp(String key, String defaultValue) {
        if (props == null) {
            props = Commons.readProps(file, "UTF-8");
        }
        return props.getProperty(key, defaultValue);
    }
}
