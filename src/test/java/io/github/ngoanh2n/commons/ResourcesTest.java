package io.github.ngoanh2n.commons;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 * @version 1.0.0
 * @since 2021-01-16
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourcesTest {

    private final String resourceName = "io/github/ngoanh2n/commons/file01.yml";

    @Test
    @Order(1)
    void existsTest() {
        assertThrows(NullPointerException.class, () -> Resources.exists(null));
        assertThrows(IllegalArgumentException.class, () -> Resources.exists(""));
        assertTrue(Resources.exists(resourceName));
    }

    @Test
    @Order(2)
    void getPathTest() {
        assertNotNull(Resources.getPath(resourceName));
        assertThrows(ResourceNotFound.class, () -> Resources.getPath("NotFoundFile.yml"));
    }

    @Test
    @Order(3)
    void getFileTest() {
        assertNotNull(Resources.getFile(resourceName));
    }

    @Test
    @Order(4)
    void getInputStreamTest() {
        assertThrows(ResourceNotFound.class, () -> Resources.getInputStream("NotFoundFile.yml"));
        assertNotNull(Resources.getInputStream(resourceName));
    }

    @Test
    @Order(5)
    void getFileToStringTest1() {
        assertThrows(ResourceNotFound.class, () -> Resources.getFileToString("NotFoundFile.yml", StandardCharsets.UTF_8));
        assertNotNull(Resources.getFileToString(resourceName, StandardCharsets.UTF_8));
    }

    @Test
    @Order(6)
    void getFileToStringTest2() {
        assertThrows(ResourceNotFound.class, () -> Resources.getFileToString("NotFoundFile.yml"));
        assertNotNull(Resources.getFileToString(resourceName));
    }
}
