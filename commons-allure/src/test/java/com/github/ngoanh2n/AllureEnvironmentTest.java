package com.github.ngoanh2n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author ngoanh2n
 */
public class AllureEnvironmentTest {
    @Test
    public void test() {
        AllureEnvironment.write("allure.properties", "log4j.properties");

        File file = new File("build/allure-results/environment.properties");
        PropertiesFile pf = new PropertiesFile(file);

        Assertions.assertEquals(9, pf.getProperties().keySet().size());
        Assertions.assertNotNull(pf.getProperty("allure.results.directory"));
        Assertions.assertNotNull(pf.getProperty("log4j.rootLogger"));
    }
}
