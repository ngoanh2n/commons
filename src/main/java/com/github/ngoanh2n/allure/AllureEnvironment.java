package com.github.ngoanh2n.allure;

import com.github.ngoanh2n.Commons;

import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2022-12-17
 */
public class AllureEnvironment {
    public static void write(Map<String, String> map, Path resultsDir) {
        Properties props = new Properties();
        props.putAll(map);
        write(props, resultsDir);
    }

    public static void write(Properties props, Path resultsDir) {
        String name = "environment.properties";
        Commons.writeProps(props, resultsDir.resolve(name).toFile());
    }
}
