package com.github.ngoanh2n.allure;

import com.github.ngoanh2n.Commons;
import io.qameta.allure.util.PropertiesUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2022-12-17
 */
public class AllureEnvironment {
    public static void write(Map<String, String> map) {
        Properties props = new Properties();
        props.putAll(map);
        write(props);
    }

    public static void write(Properties props) {
        Path resultsDir = getResultsDir();
        write(props, resultsDir);
    }

    public static void write(Properties props, Path resultsDir) {
        String name = "environment.properties";
        File file = resultsDir.resolve(name).toFile();
        Commons.writeProps(props, file);
    }

    private static Path getResultsDir() {
        Properties props = PropertiesUtils.loadAllureProperties();
        return Paths.get(props.getProperty("allure.results.directory", "allure-results"));
    }
}
