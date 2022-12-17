package com.github.ngoanh2n.allure;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.Resource;
import io.qameta.allure.util.PropertiesUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2022-12-17
 */
public final class AllureEnvironment {
    public static void write(Properties... props) {
        Path resultsDir = getResultsDir();
        write(resultsDir, props);
    }

    public static void write(String... resourceNames) {
        Path resultsDir = getResultsDir();
        write(resultsDir, resourceNames);
    }

    public static void write(Path resultsDir, String... resourceNames) {
        Properties[] props = toPropsList(resourceNames);
        write(resultsDir, props);
    }

    public static void write(Path resultsDir, Properties... props) {
        String name = "environment.properties";
        File file = resultsDir.resolve(name).toFile();
        Commons.writeProps(mergeProps(props), file);
    }

    //===============================================================================//

    private static Path getResultsDir() {
        Properties props = PropertiesUtils.loadAllureProperties();
        return Paths.get(props.getProperty("allure.results.directory", "allure-results"));
    }

    private static Properties mergeProps(Properties... props) {
        return Stream.of(props).collect(Properties::new, Map::putAll, Map::putAll);
    }

    private static Properties[] toPropsList(String... resourceNames) {
        List<Properties> propsList = new ArrayList<>();
        for (String resourceName : resourceNames) {
            Properties props = Commons.readProps(resourceName);
            propsList.add(props);
        }
        return propsList.toArray(new Properties[]{});
    }
}
