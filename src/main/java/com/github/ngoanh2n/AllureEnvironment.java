package com.github.ngoanh2n;

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
 * Write {@code environment.properties} to Allure results directory.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public final class AllureEnvironment {
    private AllureEnvironment() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    /**
     * Write {@linkplain Properties} to {@code environment.properties} at Allure results directory.
     *
     * @param props is list of {@linkplain Properties} to be written.
     */
    public static void write(Properties... props) {
        Path resultsDir = getResultsDir();
        write(resultsDir, props);
    }

    /**
     * Write resource properties file to {@code environment.properties} at Allure results directory.
     *
     * @param resourceNames is list of resource properties file to be written.
     */
    public static void write(String... resourceNames) {
        Path resultsDir = getResultsDir();
        write(resultsDir, resourceNames);
    }

    /**
     * Write resource properties file to {@code environment.properties} at Allure results directory.
     *
     * @param resultsDir    is Allure results directory.
     * @param resourceNames is list of resource properties file to be written.
     */
    public static void write(Path resultsDir, String... resourceNames) {
        Properties[] props = toPropsList(resourceNames);
        write(resultsDir, props);
    }

    /**
     * Write {@linkplain Properties} to {@code environment.properties} at Allure results directory.
     *
     * @param resultsDir is Allure results directory.
     * @param props      is list of {@linkplain Properties} to be written.
     */
    public static void write(Path resultsDir, Properties... props) {
        String name = "environment.properties";
        File file = resultsDir.resolve(name).toFile();
        Commons.writeProps(mergeProps(props), file);
    }

    //-------------------------------------------------------------------------------//

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
