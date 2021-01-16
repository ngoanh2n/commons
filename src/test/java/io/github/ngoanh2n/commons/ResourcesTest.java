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
    void exists() {
        assertThrows(NullPointerException.class, () -> Resources.exists(null));
        assertThrows(IllegalArgumentException.class, () -> Resources.exists(""));
        assertTrue(Resources.exists(resourceName));
    }

    @Test
    @Order(2)
    void path() {
        assertNotNull(Resources.path(resourceName));
        assertThrows(ResourceNotFound.class, () -> Resources.path("NotFoundFile.yml"));
    }

    @Test
    @Order(3)
    void file() {
        assertNotNull(Resources.file(resourceName));
    }

    @Test
    @Order(4)
    void inputStream() {
        assertThrows(ResourceNotFound.class, () -> Resources.inputStream("NotFoundFile.yml"));
        assertNotNull(Resources.inputStream(resourceName));
    }

    @Test
    @Order(5)
    void fileToString() {
        assertThrows(ResourceNotFound.class, () -> Resources.fileToString("NotFoundFile.yml", StandardCharsets.UTF_8));
        assertNotNull(Resources.fileToString(resourceName, StandardCharsets.UTF_8));
    }

    @Test
    @Order(6)
    void fileToString2() {
        assertThrows(ResourceNotFound.class, () -> Resources.fileToString("NotFoundFile.yml"));
        assertNotNull(Resources.fileToString(resourceName));
    }
}
