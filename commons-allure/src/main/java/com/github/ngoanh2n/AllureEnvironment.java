package com.github.ngoanh2n;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Write {@code environment.properties} to Allure results directory.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/commons">ngoanh2n/commons</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/commons-allure">com.github.ngoanh2n:commons-allure</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2019
 */
public final class AllureEnvironment {
    private AllureEnvironment() { /**/ }

    //-------------------------------------------------------------------------------//

    /**
     * Write {@link Properties} to {@code environment.properties} at Allure results directory.
     *
     * @param props The list of {@link Properties}s to be written.
     */
    public static void write(Properties... props) {
        Path resultsDir = getResultsDir();
        write(resultsDir, props);
    }

    /**
     * Write resource properties file to {@code environment.properties} at Allure results directory.
     *
     * @param resourceNames The list of resource properties files to be written.
     */
    public static void write(String... resourceNames) {
        Path resultsDir = getResultsDir();
        write(resultsDir, resourceNames);
    }

    /**
     * Write resource properties file to {@code environment.properties} at Allure results directory.
     *
     * @param resultsDir    Allure results directory.
     * @param resourceNames The list of resource properties files to be written.
     */
    public static void write(Path resultsDir, String... resourceNames) {
        Properties[] props = toPropsList(resourceNames);
        write(resultsDir, props);
    }

    /**
     * Write {@link Properties} to {@code environment.properties} at Allure results directory.
     *
     * @param resultsDir Allure results directory.
     * @param props      The list of {@link Properties}s to be written.
     */
    public static void write(Path resultsDir, Properties... props) {
        String name = "environment.properties";
        File file = resultsDir.resolve(name).toFile();
        Commons.writeProps(mergeProps(props), file);
    }

    //-------------------------------------------------------------------------------//

    private static Path getResultsDir() {
        PropertiesFile propertiesFile = new PropertiesFile("allure.properties");
        String resultsDir = propertiesFile.getProperty("allure.results.directory");

        if (resultsDir == null) {
            resultsDir = "build/allure-results";
        }
        return Paths.get(resultsDir);
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
